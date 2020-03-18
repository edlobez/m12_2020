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

import dawm12.grup2.es.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez
 * Controlador común para todos los usuarios
 * Motrará retornará el listado de usuarios.
 * En el caso de solicitarlo el admin o el veterinario retornará todos los 
 * animales.
 * En el caso de solicitarlo el responsable o voluntario, retornará sólo los
 * animales de su tipo.
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
    
    
    @RequestMapping(value = "/animalList") 
    public ModelAndView animalList () {
        ModelAndView modelview = new ModelAndView("listaAnimales");
        modelview.addObject("rol", "admin");
        return modelview;    
    }
    
    /*
    Retorna en formato Json la lista de animales solicitada
    */
    @RequestMapping(value = "/getAnimalList")
    public String getSearchResultViaAjax() {
        
        String result = "{\"current\":1, "
                + "\"rowCount\":10,"
                + "\"total\":14,"
                + "\"rows\": ["
                + "{\"idanimal\":1,\"nom\":\"El nombre\",\"tipusanimal\": \"edu\",\"raza\": \"edu@yahoo.com\"}"                               
                + "]}";
        
        return result;
        
    }
    
}
