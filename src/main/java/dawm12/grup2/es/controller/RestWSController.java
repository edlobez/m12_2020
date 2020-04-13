/*
 * Copyright (C) 2020 sgascon
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
import dawm12.grup2.es.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sgascon
 */

@RestController
@RequestMapping(value="/animals")
public class RestWSController {
    
    @Autowired @Qualifier("animalService")
    private Service animalService;
    
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Animal> getAll() {
        return this.animalService.getAll();
    }
    
    @RequestMapping(value = "/fecha/{codi}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Animal getbyCodi(@PathVariable String codi) {
        return (Animal) animalService.getone("idAnimal=" + codi);
    }
    
 
    

 }
