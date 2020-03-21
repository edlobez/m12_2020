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
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
        
        System.out.println("Editando animal: " + an.toString());
        
        
        
        return mv;
    }
    
    @RequestMapping(value="/saveAnimal") 
    public ModelAndView saveAnimal (
            @ModelAttribute("animal") Animal animal
    ) {
        
        System.out.println ("Salvando de un update el animal: " + animal.toString());
        
        
        return null;
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
    Retorna en formato Json la lista de animales solicitada
     */
    @RequestMapping(value = "/getAnimalList")
    public String getSearchResultViaAjax(
            @RequestParam("current") String actual,
            @RequestParam("rowCount") String numFilas,
            HttpServletRequest request,
            @RequestParam("searchPhrase") String cadenaBusqueda)throws JSONException {

        
        String busqueda_por = "nom";
        Enumeration <String> par = request.getParameterNames();
        while (par.hasMoreElements()) {
            String aux = par.nextElement();
            if (aux.indexOf("sort") != -1) {
                busqueda_por = aux.substring(5, aux.length()-1);                
                if ( busqueda_por.equals("tAnimal") ) {
                    busqueda_por = "tipusAnimal";
                }
            }
        }
        
       // System.out.println("\n\n"+busqueda_por);
        
        List <Raza> lasRazas = new ArrayList <>();
        
        if ( busqueda_por.equals("tipusAnimal")) {
            if ( cadenaBusqueda.toLowerCase().startsWith("g") ) 
                cadenaBusqueda = "1";
            else if ( cadenaBusqueda.toLowerCase().startsWith("c") )
                cadenaBusqueda = "2";
            else if ( cadenaBusqueda.toLowerCase().startsWith("t") ) 
                cadenaBusqueda = "3";
        } else if ( busqueda_por.equals("laRaza")) { 
            lasRazas = razaService.get("descripcio=%" + cadenaBusqueda + "%");
            busqueda_por = "raza";
           
        }
        
        
        List<Animal> lista = new ArrayList<>();
        if ( cadenaBusqueda.length() == 0 ) {
            String aux = "";
            if ( Integer.parseInt(numFilas) != -1 ) {
                aux = "LIMIT " + numFilas;
            }
            lista = animalService.get(aux, "inactiu=0");
        } else {
            if (lasRazas == null || lasRazas.isEmpty())
                lista = animalService.get("ORDER BY nom ASC", busqueda_por + "=%" + cadenaBusqueda + "%,inactiu=0");
            else {
                for (Raza unaRaza: lasRazas) {
                    //System.out.println("\nRazas: " + unaRaza.getDescripcio());
                    List<Animal> lista_aux = new ArrayList<>();
                    cadenaBusqueda = Integer.toString( ( (Raza) razaService.getone("descripcio=" + unaRaza.getDescripcio())).getIdRaza());
                   // System.out.println("buscar por " + busqueda_por + ": " + cadenaBusqueda);
                    lista_aux = animalService.get("ORDER BY nom ASC", busqueda_por + "=%" + cadenaBusqueda + "%.inactiu=0");
                    lista.addAll(lista_aux);
                }
                
            }
        }
        
        if ( lista!= null && lista.size() > 0 ) {
            for (int i = 0; i < lista.size(); i++) {
                TipusAnimal t = (TipusAnimal) tipusAnimalService.getone("idtipus=" + lista.get(i).getTipusAnimal());
                Raza r = (Raza) razaService.getone("idraza=" +  lista.get(i).getRaza());
                lista.get(i).settAnimal(  t.getDescripcio()  );
                lista.get(i).setLaRaza(r.getDescripcio());
            }
        }
        
        
        ObjectMapper JSON_MAPPER = new ObjectMapper();
        JSONObject member = null;
        JSONArray array = new JSONArray();
        for (Animal an : lista) {
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
            json.put("total", lista.size());
            json.put("rows", array);            
            json.put("rowCount", numFilas);
            json.put("current", actual);
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
