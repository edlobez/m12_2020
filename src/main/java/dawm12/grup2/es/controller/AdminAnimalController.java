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
import dawm12.grup2.es.domain.Comentari;
import dawm12.grup2.es.domain.Raza;
import dawm12.grup2.es.domain.Roles;
import dawm12.grup2.es.domain.TipusAnimal;
import dawm12.grup2.es.domain.Usuarios;
import dawm12.grup2.es.service.Service;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @Autowired @Qualifier("animalService")
    private Service animalService;
    
    @Autowired @Qualifier("comentariService")
    private Service comentariService;
    
    @RequestMapping("/newAnimal")
    public ModelAndView newAnimal (ModelMap modelo) {
        ModelAndView mv = new ModelAndView("animal");
        Animal an = new Animal();
        mv.addObject("animal", an);
        mv.addObject("accion", "create");
        
        cargarDatosEnVista ( modelo );
        
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
    
    Se deben rellenar los valores correctos para 
     - tipusAnimal
     - raza
     - vetAsignat
    esto lo realizamos con el método completarCampos
    */
    @RequestMapping(value = "/saveAnimal", method = RequestMethod.POST) 
    public ModelAndView saveAnimal (
           @Valid @ModelAttribute ("animal") Animal animal,
           BindingResult validacion, 
           HttpServletRequest request,
           @RequestParam("accion") String accion,
           @RequestParam("comentari") String comentario,
           ModelMap modelo
    )  {
        
        if (validacion.hasErrors()) {
            for (Object error : validacion.getAllErrors()) {
                System.out.println( error.toString());
                if ( error.toString().contains("dataNaix") ) {                   
                   modelo.addAttribute("error", "fecha_error");
                }
            }
            
            modelo.addAttribute("animal", animal);
            modelo.addAttribute("accion", accion);
            //modelo.addAttribute("error", "create_error");
            cargarDatosEnVista ( modelo );
            return new ModelAndView("animal");            
        }
        
        animal = completarCampos (animal); 
                
        // Creamos un nuevo animal
        if ( accion.equals("create")) {
            Animal an = createAnimal(animal, modelo);
            if ( an == null ) {
                modelo.addAttribute("animal", animal);
                modelo.addAttribute("accion", accion);
                modelo.addAttribute("error", "create_error");
                cargarDatosEnVista ( modelo );
                return new ModelAndView("animal");
            }else {
                // Debemos guardar el comentario
                if ( comentario!= null && comentario.length() > 0 )
                    if ( guardarComentario ( an, comentario) == null )
                        System.out.println("Error en creando comentario");
            }            
            
        }
        
        String param = "";
        if ( accion.equals("create") )
            param = "?param=create_ok";
        if ( accion.equals("update") ) 
            param = "?param=update_ok";
        
        
        return new ModelAndView("redirect:/animal/animalList" + param);
    }
    
    @RequestMapping(value = "/deleteAnimal")
    public ModelAndView deleteAnimal(
            @RequestParam("idanimal") int idanimal) {
        
        Animal an = (Animal) animalService.getone("idanimal="+idanimal);
        System.out.println ("Borrar animal: " + an.toString());
        
        // Para borrar el animal lo deshabílitamos        
        an = (Animal) animalService.update(an, "inactiu=1");
        
        String param;
        if ( an == null) {
            param = "?param=delete_Nok";
        }
        else {
            param = "?param=delete_ok";
        }        
        
        return new ModelAndView("redirect:/animal/animalList" + param);
    }
    
    /*
    Carga los datos en la vista de diferentes campos
    */
    private void cargarDatosEnVista ( ModelMap modelo ) {
        
         //El literal del tipo de animal
         // En el caso de ser un responsable, sólo se le cargarán
         // ... animales de su mismo tipo
        String filtro_tanimal = null;
         if ( rolActual ().equals("responsable") ) {
             int aux_tanimal = ((Usuarios)usuarioService.getone("username="+ usuarioActual())).getTipusAnimal();
             filtro_tanimal = "idtipus=" + aux_tanimal;
         }
            
        List <String> tAnimal = new ArrayList <>();
        // si el metodo get recibe un null actua igual que le método getAll
        for (Object unAnimal : tipusAnimalService.get(filtro_tanimal) ) {
            tAnimal.add( ((TipusAnimal) unAnimal).getDescripcio() );
        }
        tAnimal.remove("Tots");
        modelo.addAttribute("tAnimal", tAnimal);
               
        //El literal de la raza, se mostrará en función del rol
        // Filtrar lista por rol
        String lista_rol = "";
        if ( rolActual().equals("voluntari") || rolActual().equals("responsable")) {            
            Usuarios u = (Usuarios) usuarioService.getone("username="+usuarioActual());
            lista_rol = "idtipus=" + u.getTipusAnimal();            
        }
        List <String> laRaza = new ArrayList <>();
        for (Object raza : razaService.get(lista_rol) ) {
            laRaza.add ( ((Raza) raza).getDescripcio() );
        }
        modelo.addAttribute("laRaza", laRaza);
        
        // Los veterinarios a asignar
        List <String> vet = new ArrayList <> ();        
        for (Object unUser : usuarioService.get("rol="+ ( (Roles)rolesService.getone("rol=veterinari") ).getIdRol() ) ) {
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
    Método que insertará el animal en la BBDD. Enviamos a parte del animal
    el modelo por si hay que lanzar alǵun error a la vista
    */
    private Animal createAnimal (Animal animal, ModelMap modelo ) {
        
        // Siempre que se cree un animal nuevo es con baja médica 
        // ... y adoptado a false
        animal.setIsAlta(false);
        animal.setIsAdoptat(false);
        animal.setInactiu(false);
        
        // La fecha de creación y el usuario que lo crea
        java.util.Date d = new java.util.Date(); 
        java.sql.Date d2 = new java.sql.Date(d.getTime());
        animal.setCreatedDate(d2);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        animal.setCreatedUser(auth.getName());
        
        animal.setDataAlta(new java.sql.Date(1999-01-01));
        
        System.out.println("Animal a guardar: " + animal.toString());
        
        return (Animal) animalService.create(animal);
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
    
    private String rolActual () {
        
        String rol = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            rol = "admin";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("responsable"))){
           rol = "responsable";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("veterinari"))){
            rol = "veterinari";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("voluntari"))){
            rol = "voluntari";
        }
        return rol;
    }
    
    private String usuarioActual () {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
   
    
 }
