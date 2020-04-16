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

import dawm12.grup2.es.domain.Adopcio;
import dawm12.grup2.es.domain.Animal;
import dawm12.grup2.es.domain.Imagen;
import dawm12.grup2.es.domain.Persona;
import dawm12.grup2.es.service.Service;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez
 */

@Controller
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
        System.out.println("Persona : " + p);
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
    
    /*
       - Añadimos los datos de persona a la base de datos.
       - Rellenamos la tabla adopción con los datos de la persona y el animal.
    */
    private boolean realizarAdopcio (Persona p, Animal n) {
        
        //Añadimos la persona
        Persona aux_p = (Persona) personaService.create(p);
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
