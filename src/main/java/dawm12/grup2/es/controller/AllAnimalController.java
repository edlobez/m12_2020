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

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dawm12.grup2.es.domain.Animal;
import dawm12.grup2.es.domain.Comentari;
import dawm12.grup2.es.domain.Raza;
import dawm12.grup2.es.domain.Roles;
import dawm12.grup2.es.domain.TipusAnimal;
import dawm12.grup2.es.domain.Usuarios;
import dawm12.grup2.es.service.Service;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez Controlador común para todos los usuarios Motrará retornará
 * el listado de usuarios. En el caso de solicitarlo el admin o el veterinario
 * retornará todos los animales. En el caso de solicitarlo el responsable o
 * voluntario, retornará sólo los animales de su tipo.
 */
@Controller
@RestController
@RequestMapping("/animal")
public class AllAnimalController {

    @Autowired
    @Qualifier("usuarioService")
    private Service usuarioService;

    @Autowired
    @Qualifier("rolesService")
    private Service rolesService;

    @Autowired
    @Qualifier("tipusAnimalService")
    private Service tipusAnimalService;

    @Autowired
    @Qualifier("animalService")
    private Service animalService;
    
    @Autowired
    @Qualifier("razaService")
    private Service razaService;  
    
    @Autowired
    @Qualifier("comentariService")
    private Service comentariService;
    
    @RequestMapping(value = "/animalList")
    public ModelAndView animalList() {
        ModelAndView modelview = new ModelAndView("listaAnimales");
        modelview.addObject("rol", "admin");
        return modelview;
    }

    @RequestMapping(value = "/editAnimal")
    public ModelAndView editAnimal (
            @RequestParam ("idanimal") int idAnimal, ModelMap modelo
    ) {
        ModelAndView mv = new ModelAndView("animal");
        Animal an = (Animal) animalService.getone("idAnimal=" + idAnimal);
        //Completamos campos para añadir a la vista
        an.setLaRaza( ((Raza)razaService.getone("idraza="+an.getRaza())).getDescripcio() );
        Usuarios usr = (Usuarios)usuarioService.getone("username="+an.getVetAssignat());
        an.setVeterinari( usr.getUsername() + " " + usr.getNombre() + " " + usr.getApellido1() );
        an.settAnimal( ((TipusAnimal)tipusAnimalService.getone("idtipus=" + an.getTipusAnimal())).getDescripcio() );
        
        modelo.addAttribute("animal", an);
        modelo.addAttribute("accion", "update");
        cargarDatosEnVista ( an, modelo);
        cargarComentarios ( an, modelo);
        
        System.out.println("Editando animal: " + an.toString());
        
        
        
        return mv;
    }
    
    @RequestMapping(value="/saveAnimal", method = RequestMethod.POST) 
    public ModelAndView saveAnimal (
           @Valid @ModelAttribute ("animal") Animal animal,
           BindingResult validacion, 
           HttpServletRequest request,
           @RequestParam("accion") String accion,
           @RequestParam("comentari") String comentario,
           ModelMap modelo
    )  {
        
        if (validacion.hasErrors()) {
            System.out.println("Error validaciones");
        }
        
        animal = completarCampos (animal); 
        
        System.out.println("El comentario: " + comentario);
        
        // Creamos un nuevo animal
        if (accion.equals("update")) {
            Animal an = updateAnimal(animal, modelo);
            if ( an == null ) {
                modelo.addAttribute("animal", animal);
                modelo.addAttribute("accion", accion);
                modelo.addAttribute("error", "create_error");
                cargarDatosEnVista (animal, modelo);
                return new ModelAndView("animal");
            }else {
                // Debemos guardar el comentario
                if ( comentario!= null && comentario.length() > 0 ){
                    if ( guardarComentario ( an, comentario) == null ){
                        System.out.println("Error en creando comentario");
                    }
                }
            }            
            
        }
        
        String param = "";
        if (accion.equals("update")) 
            param = "?param=update_ok";
        
        
        return new ModelAndView("redirect:/animal/animalList" + param);
        
        
        
        //System.out.println ("Salvando de un update el animal: " + animal.toString());
        
        
        //return null;
    }
    
        private Animal updateAnimal (Animal animal, ModelMap modelo) {
        
        // TODO --> En la modificación debería de coger de la vista si le da el alta, si esta adoptado y si es inactivo
        animal.setIsAlta(false);
        animal.setIsAdoptat(false);
        animal.setInactiu(false);
        
        // TODO --> La fecha la debe coger del formulario
        animal.setDataAlta(new java.sql.Date(1999-01-01));
        
        return (Animal) animalService.update(animal, 
                "nom=" + animal.getNom() + ","+
                "datanaix=" + animal.getDataNaix() + ","+
                "sexe="+animal.getSexe()+","+
                "tamany="+animal.getTamany()+","+
                "tipusAnimal="+animal.getTipusAnimal()+","+
                "raza="+animal.getRaza()+","+
                "isalta="+((animal.isIsAlta())?1:0)+","+
                "dataalta="+animal.getDataAlta()+","+
                "isadoptat="+((animal.isIsAdoptat())?1:0)+","+
                "isvacunat="+((animal.isIsVacunat())?1:0)+","+
                "isesterilitzat="+((animal.isIsEsterlitzat())?1:0)+","+
                "haschip="+((animal.isHasChip())?1:0)+","+
                "numchip="+animal.getNumChip()+","+       
                "vetassignat="+animal.getVetAssignat()+","+
                "inactiu="+((animal.isInactiu())?1:0));
    }
    
    /*
    Carga los comentarios que exists en la tabla comentarios para el animal
    en concreto.
    */
    private void cargarComentarios ( Animal an, ModelMap modelo ) {
        // Comamos los comentarios para ese animal
        List <Comentari> comentarios = comentariService.getAND("ORDER BY CREATEDDATE", "idanimal="+an.getIdAnimal());
        if ( comentarios != null && comentarios.size() > 0 ) {
            modelo.addAttribute("comentarios", comentarios);
        }
        
        
    }
    
    /*
    Carga los datos en la vista de diferentes campos
    El primer elemento a mostra es el valor actual guardado
    */
    private void cargarDatosEnVista ( Animal an, ModelMap modelo ) {  
        
         //El literal del tipo de animal
        List <String> tAnimal = new ArrayList <>();
        tAnimal.add(an.gettAnimal());
        for (Object unAnimal : tipusAnimalService.getAll() ) {
            String aux = ((TipusAnimal) unAnimal).getDescripcio();
            if ( !aux.equals(an.gettAnimal()) )
                tAnimal.add( aux );
        }
        tAnimal.remove("Tots");
               
        modelo.addAttribute("tAnimal", tAnimal);
               
        //El literal de la raza
        List <String> laRaza = new ArrayList <>();
        laRaza.add ( an.getLaRaza() ); 
        for (Object raza : razaService.getAll() ) {
            String aux = ((Raza) raza).getDescripcio();
            if ( !aux.equals(an.getLaRaza()) )
                 laRaza.add ( aux );
        }
       
        modelo.addAttribute("laRaza", laRaza);
        
        // Los veterinarios a asignar
        List <String> vet = new ArrayList <> ();
        vet.add ( an.getVeterinari() );
        for (Object unUser : usuarioService.get("rol="+ ( (Roles)rolesService.getone("rol=veterinari") ).getIdRol() ) ) {
            if ( !((Usuarios)unUser).getUsername().equals(an.getVetAssignat())  )
                 vet.add ( ((Usuarios) unUser).getUsername() + " " + ((Usuarios) unUser).getNombre() + " " + ((Usuarios) unUser).getApellido1());            
        } 
        modelo.addAttribute("vet", vet);
        
        
    }
        /*
    Hay alguno campos de la estructura de datos que se deben rellenar en 
    función del otros. Estos son:
    - tipusAnimal, es el ID que referencia a la tabla tipusAnimal. Este campo 
    se debe rellenar en función del valor tAnimal.
    - raza, es el ID que referencia a la tabal raza. Este campo se debe rellenar
    en función del valor laRaza
    - vetAsignat, es el ID que referencia a la tabla usuarios. Este campo se debe
    rellenar en función del valor veterinari.
    */
    private Animal completarCampos ( Animal animal ) {
        
        TipusAnimal ta = (TipusAnimal) tipusAnimalService.getone( "descripcio=" + animal.gettAnimal() );
        animal.setTipusAnimal( ta.getIdTipus() );
        
        Raza r = (Raza) razaService.getone("descripcio=" + animal.getLaRaza() );
        animal.setRaza( r.getIdRaza() );
        
        Usuarios u = (Usuarios) usuarioService.getone( "username=" + animal.getVeterinari().split(" ")[0] );
        animal.setVetAssignat( u.getUsername() );
        
        return animal;
        
    }
    
        /*
    Guarda el comentario para un animal nuevo
    Se almacenará
    [FECHA - HORA][USERNAME] - COMENTARIO
    */
    private Comentari guardarComentario (Animal an, String comentario) {
        
        String usuario = an.getCreatedUser();
        java.util.Date d = new java.util.Date(); 
        java.sql.Date d2 = new java.sql.Date(d.getTime());
        java.sql.Timestamp date = new java.sql.Timestamp(d.getTime());
        
        //comentario = "[" + date + "] [" + usuario + "] - " + comentario;
 
       // System.out.println("\nComentario:" + comentario +"\n para animal id " + an.getIdAnimal());
        
        // public Comentari(int idComentari, String descripcio, int idAnimal, Date createdDate, String createdUser)
        
        Comentari c = (Comentari) comentariService.create( new Comentari ( comentario, an.getIdAnimal(), d2, an.getCreatedUser()) );
        
        //System.out.println ("Comentario " + c.toString());
        return c;
        
    } 
    
    
    /*
    Retorna en formato Json la lista de animales solicitada
     */   
    @RequestMapping(value = "/getAnimalList")
    public String getSearchResultViaAjax (
           HttpServletRequest request 
    ) throws JSONException {
        
        String campos_tabla [] = {"idAnimal", "nom", "tipusAnimal", "raza"};
        
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
        
        List <Raza> lasRazas = new ArrayList <>();
        
        if ( busqueda_por.equals("tipusAnimal")) {            
            if ( cadenaBusqueda.toLowerCase().startsWith("ga") )
                cadenaBusqueda = "2";
            if ( cadenaBusqueda.toLowerCase().startsWith("go") )
                cadenaBusqueda = "1";
            if ( cadenaBusqueda.toLowerCase().startsWith("g") ) 
                cadenaBusqueda = "1";
            if ( cadenaBusqueda.toLowerCase().startsWith("t") ) 
                cadenaBusqueda = "3";
        } else if ( busqueda_por.equals("raza")) { 
            lasRazas = razaService.get("descripcio=%" + cadenaBusqueda + "%");
            busqueda_por = "raza";
           
        }
        
        // Cadena complementario a la busqueda      
        String aux = "ORDER BY " + campos_tabla[buscar_por] + " " + order_dir + " ";
        
        List <Animal> _animales = new ArrayList<>();
        _animales = animalService.get(aux, "inactiu=0");
        //Obtenemos el total de animales sin filtro.
        int total_registros = _animales.size();        
        if (cadenaBusqueda.length() > 0 ) {
            //System.out.println("Busqueda por: " + busqueda_por + " Cadena: " + cadenaBusqueda);
            if (lasRazas == null || lasRazas.isEmpty())
                _animales = animalService.getAND(aux, busqueda_por + "=%" + cadenaBusqueda + "%,inactiu=0");
            else {
                _animales.clear();
                for (Raza unaRaza: lasRazas) {                    
                    //System.out.println("\nRazas: " + unaRaza.getDescripcio());
                    List<Animal> lista_aux = new ArrayList<>();
                    cadenaBusqueda = Integer.toString( ( (Raza) razaService.getone("descripcio=" + unaRaza.getDescripcio())).getIdRaza());
                    //System.out.println("buscar por " + busqueda_por + ": " + cadenaBusqueda);
                    lista_aux = animalService.getAND(aux, busqueda_por + "=%" + cadenaBusqueda + "%,inactiu=0");
                    _animales.addAll(lista_aux);
                }                
            }            
        } 
        
        
        int reg_final = inicio + num_registros;
        if ( reg_final > _animales.size() ) reg_final = _animales.size();
        List <Animal> animales = _animales.subList(inicio, reg_final);
        
        if ( animales != null && animales.size() > 0 ) {
            for (int i = 0; i < _animales.size(); i++) {
                TipusAnimal t = (TipusAnimal) tipusAnimalService.getone("idtipus=" + animales.get(i).getTipusAnimal());
                Raza r = (Raza) razaService.getone("idraza=" +  animales.get(i).getRaza());
                animales.get(i).settAnimal( t.getDescripcio() );
                animales.get(i).setLaRaza( r.getDescripcio()) ;
            }
            
        }
        
        ObjectMapper JSON_MAPPER = new ObjectMapper();
        JSONObject member = null;
        JSONArray array = new JSONArray();
        for (Animal an : animales) {
            try {
                member = new JSONObject(JSON_MAPPER.writeValueAsString(an));
                array.put(member);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //System.out.println("Json array: " + array.toString());
        JSONObject json = new JSONObject();
        try {
            json.put("recordsFiltered", _animales.size());            
            json.put("recordsTotal", total_registros);
            json.put("draw", draw);
            json.put("data", array);
        } catch (JSONException ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

       /* String result = "{\"current\":1, "
                + "\"rowCount\":10,"
                + "\"total\":14,"
                + "\"rows\": ["
                + "{\"idanimal\":1,\"nom\":\"El nombre\",\"tipusanimal\": \"edu\",\"raza\": \"edu@yahoo.com\"}"
                + "]}";
*/
       //System.out.println ("\n\n" + json.toString());
       return json.toString();
    }

}
