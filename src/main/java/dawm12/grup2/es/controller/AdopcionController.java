/*
 * Copyright (C) 2020 edlobez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dawm12.grup2.es.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dawm12.grup2.es.domain.Adopcio;
import dawm12.grup2.es.domain.Animal;
import dawm12.grup2.es.domain.Imagen;
import dawm12.grup2.es.domain.Persona;
import dawm12.grup2.es.service.Service;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez
 */

//TODO: @RequestMapping(value = "/adoptionsList")

@Controller
@RestController
@RequestMapping("/adopcion")
public class AdopcionController {
    
    @Autowired @Qualifier("animalService")
    private Service animalService;
    
    @Autowired @Qualifier("imagenService")
    private Service imagenService;
    
    @Autowired @Qualifier("adopcioService")
    private Service adopcionService;
    
    @Autowired @Qualifier("personaService")
    private Service personaService;
    
    private Animal an; // El animal a adoptar  
    
    
    @RequestMapping("/formulario")
    public ModelAndView formulario (
           @RequestParam("idanimal") String id_animal
           
    ) {        
        ModelAndView mv = new ModelAndView("formAdopcion");        
        Persona p = new Persona();
        an = (Animal)animalService.getone("idanimal="+id_animal);
        mv.addObject("adoptante", p);
        cargarAnimalEnVista( an, mv );
        return mv;
    }
    
    @RequestMapping("/saveAdopcio")
    public ModelAndView saveAdopcio (
            @Valid @ModelAttribute ("adoptante") Persona p,             
            BindingResult validacion
    ) {
        String param = "";
        //System.out.println("Persona : " + p);
        ModelAndView mv = new ModelAndView();  
        if (validacion.hasErrors()) {
            //System.out.println("Error validaciones");
            mv.setViewName("formAdopcion");
            mv.addObject("adoptante", p);
            cargarAnimalEnVista( an, mv );            
            return mv;
        }
        else  if ( realizarAdopcio(p, an) == true) {
            param = "?param=adopcio_ok";
        }
        else param = "?param=adopcio_Nok";
            
        
        return new ModelAndView("redirect:/" + param);
        
    }
    
    @RequestMapping("/readoptar")
    public ModelAndView readoptar (
        @RequestParam("idpersona") String id_persona
    ) {
       // System.out.println("readoptar: " + id_persona + "idanimal: " + an.getIdAnimal());
        String param = "";
        Persona p = (Persona) personaService.getone("idpersona=" + id_persona);
        System.out.println("readoptar: " + p.getNom() + "idanimal: " + an.getIdAnimal());
        
        if ( p!=null && realizarAdopcio(p, an) == true) {
            param = "?param=adopcio_ok";
        }
        else param = "?param=adopcio_Nok";
            
        
        return new ModelAndView("redirect:/" + param);
    }
    
    @RequestMapping("/listaAdoptantes")
    public String listaAdoptantes (
            HttpServletRequest request
    ) throws JSONException {
        
        String campos_tabla [] = {"nom", "cognom1", "cognom2", "email", "telefon", "direccio"};
        
        // Cada petición debemos sumar 1 a este parámetro
        int draw = Integer.parseInt(request.getParameter("draw")) + 1;
        
        // Número de registros a mostrar
        int num_registros = Integer.parseInt(request.getParameter("length"));
        
        // Primer registro a mostrar, depende de la página donde estamos
        int inicio = Integer.parseInt(request.getParameter("start"));
        
        // Miramos si hay algún filtro
        String cadenaBusqueda = request.getParameter("search[value]");
        
        // La columna por la que buscar
        int buscar_por = Integer.parseInt( request.getParameter("order[0][column]") );
        String busqueda_por = campos_tabla[buscar_por];
        
        //Ordernar ascendenteo o descendente
        String order_dir = request.getParameter("order[0][dir]");
        
        // Cadena complementario a la busqueda      
        String aux = "ORDER BY " + campos_tabla[buscar_por] + " " + order_dir + " ";
        
        List <Persona> _personas = new ArrayList <> ();
        _personas = personaService.getAll(aux);
        // Todos los registros sin filtro
        int total_registros = _personas.size();
        
        if (cadenaBusqueda.length() > 0 ) {            
            _personas = personaService.getAND(aux, busqueda_por + "=%" + cadenaBusqueda + "%");
        }
        
        int reg_final = inicio + num_registros;
        if ( reg_final > _personas.size() ) reg_final = _personas.size(); 
        List <Persona> personas = _personas.subList(inicio, reg_final);
        
        ObjectMapper JSON_MAPPER = new ObjectMapper();
        JSONObject member = null;
        JSONArray array = new JSONArray();
        for (Persona user : personas) {
            try {
                member = new JSONObject(JSON_MAPPER.writeValueAsString(user));
                array.put(member);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(AdopcionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //System.out.println("Json array: " + array.toString());
        JSONObject json = new JSONObject();
        try {            
            json.put("recordsFiltered", _personas.size());            
            json.put("recordsTotal", total_registros);
            json.put("draw", draw);
            json.put("data", array);
        } catch (JSONException ex) {
            Logger.getLogger(AdopcionController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return json.toString();
    }
    
    /*
       - Añadimos los datos de persona a la base de datos.
       - Rellenamos la tabla adopción con los datos de la persona y el animal.
    */
    private boolean realizarAdopcio (Persona p, Animal n) {
        
        //Añadimos la persona siempre y cuando no exista ya que puede ser una
        //... readopcion
        Persona aux_p = null;
        aux_p = (Persona) personaService.getone("idpersona=" + p.getIdpersona() );
        if ( aux_p == null )
             aux_p = (Persona) personaService.create(p);
        
        if ( aux_p == null ) return false;
        
        // Rellenamos la tabla adopcio
        //public Adopcio(int idAnimal, int idPersona, Date dataAdopcio)
        java.util.Date d = new java.util.Date(); 
        java.sql.Date d2 = new java.sql.Date(d.getTime());
        Adopcio ap = new Adopcio (n.getIdAnimal(), aux_p.getIdpersona(), d2 );
        if ( adopcionService.create(ap) == null ) return false;
        
        // Modificamos ese animal como adoptado
        if ( animalService.update(n, "isadoptat=1") == null) return false;
        
        System.out.println("adopción correcta");
        
        return true;
    }
    
    //Cargamos en el modelo las fotos del animal y su nombre
    private void cargarAnimalEnVista ( Animal an, ModelAndView modelo) {
        
        //Cargamos las imágenes
        List <Imagen> imagenes = imagenService.get("idanimal="+an.getIdAnimal());
        
        List <Imagen> images = new ArrayList <>();
        for(Imagen img : imagenes){
            byte[] bytes = (byte[]) img.getPixel();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            img.setBase64(base64);

            images.add(img);
        }
        
        if (images != null && images.size() > 0 ) {
             modelo.addObject("imagenes", images);
        }
        
        modelo.addObject("nombre_animal", an.getNom());
        
    }
    
}
