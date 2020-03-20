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

import dawm12.grup2.es.domain.Animal;
import dawm12.grup2.es.domain.Raza;
import dawm12.grup2.es.domain.Roles;
import dawm12.grup2.es.domain.TipusAnimal;
import dawm12.grup2.es.domain.Usuarios;
import dawm12.grup2.es.service.Service;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez
 */

@Controller
@RequestMapping("/responsable")
public class AdminAnimalController {
    
    @Autowired @Qualifier("tipusAnimalService")  
    private Service tipusAnimalService;
    
    @Autowired @Qualifier("razaService")
    private Service razaService;  
    
    @Autowired @Qualifier("usuarioService")
    private Service usuarioService;
    
    @Autowired @Qualifier("rolesService")
    private Service rolesService;
    
    @RequestMapping("/newAnimal")
    public ModelAndView newAnimal () {
        ModelAndView mv = new ModelAndView("animal");
        Animal an = new Animal();
        mv.addObject("animal", an);
        mv.addObject("accion", "create");
        
        //El literal del tipo de animal
        List <String> tAnimal = new ArrayList <>();
        for (Object unAnimal : tipusAnimalService.getAll() ) {
            tAnimal.add( ((TipusAnimal) unAnimal).getDescripcio() );
        }
        tAnimal.remove("Tots");
        mv.addObject("tAnimal", tAnimal);
        
        //El literal de la raza
        List <String> laRaza = new ArrayList <>();
        for (Object raza : razaService.getAll() ) {
            laRaza.add ( ((Raza) raza).getDescripcio() );
        }        
        mv.addObject("laRaza", laRaza);
        
        // Los veterinarios a asignar
        List <String> vet = new ArrayList <> ();        
        for (Object unUser : usuarioService.get("rol="+ ( (Roles)rolesService.getone("rol=veterinari") ).getIdRol() ) ) {
            vet.add ( ((Usuarios) unUser).getUsername() + " " + ((Usuarios) unUser).getNombre() + " " + ((Usuarios) unUser).getApellido1());            
        }        
        mv.addObject("vet", vet);       
        
        return mv;
        
    }
    
    /*
    En el momento de guardar un animal nuevo se deben crear los campos
    - createddate; fecha de creacion
    - createduser; el usuario que lo crea
    - inactiu = true
    - isalta = false
    - dataalta = null;
    - isadoptat = false;
    
    Y si no están rellenos los campos siguientes, se rellenan con el siguiente 
    ...valor
    - isvacunat = false;
    - isesterilitzat = false;
    - haschip = false;
    - numchip = null;
    */
    @RequestMapping(value = "/saveAnimal", method = RequestMethod.POST) 
    public ModelAndView saveAnimal (
           @Valid @ModelAttribute ("animal") Animal animal,
           BindingResult validacion, 
           HttpServletRequest request
    )  {
        
        if (validacion.hasErrors()) {
            System.out.println("Error validaciones");
        }
        
        String aux = null;
        try {
            aux = new String ( animal.getLaRaza().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AdminAnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\n"+ aux);
        
        System.out.println("Codificacion: " + request.getCharacterEncoding());
        System.out.println("la raza " + request.getParameter("laRaza"));
        
        System.out.println("Animal a guardar: " + animal.toString());
        
        
        return null;
    }
    
    
 }
