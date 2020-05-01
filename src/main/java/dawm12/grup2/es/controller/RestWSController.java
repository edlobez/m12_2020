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

import dawm12.grup2.es.domain.Adopcio;
import dawm12.grup2.es.domain.Animal;
import dawm12.grup2.es.domain.AnimalBean;
import dawm12.grup2.es.domain.EstadisicasBean;
import dawm12.grup2.es.domain.Raza;
import dawm12.grup2.es.domain.TipusAnimal;
import dawm12.grup2.es.service.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @Autowired @Qualifier("tipusAnimalService")
    private Service tipusAnimalService;
    
    @Autowired @Qualifier("razaService")
    private Service razaService;
    
    @Autowired @Qualifier("adopcioService")
    private Service adopcioService;
    
    private final String CAMP_ADOPTAT = "isAdoptat";
    private final String CAMP_INACTIU = "inactiu";
    private final String CAMP_ALTA = "isAlta";
    private final String CAMP_DATA_CREACIO = "createdDate";
    private final String CAMP_DATA_NAIX = "dataNaix";
    private final String CAMP_TAMANY = "tamany";
    private final String CAMP_RAZA = "raza";
    private final String CAMP_TIPUS_ANIMAL = "tipusAnimal";
    private final String CAMP_SEXE = "sexe";
    private final String CAMP_ID_ANIMAL = "idAnimal";
    
    
    private final String CAMP_DESC = "descripcio";
    private final String CAMP_ID_TIPUS = "idtipus";
    private final String CAMP_ID_RAZA = "idraza";
    
    
    
    
    
    /* TODO, no me acaba de gustar que se mmuestren todos los animales. Haré que se muestre solo los animales que están actualmente es decir:
    - No adoptados
    - Dados de alta, están para adoptar.
    - No inactivos
    */
   /* @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Animal> getAll() {
        return this.animalService.getAll();
    }*/
    
    /* Listado de animales que están para adoptar (no adoptados, dados de alta y activos) */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<AnimalBean> getAnimalsAdoptar(@RequestParam (required = false) String any, @RequestParam (required = false) String tamany, @RequestParam (required = false) String raza, @RequestParam (required = false) String especie,@RequestParam (required = false) String sexe) {
        int valor_adoptat = 0; // No esté adoptado
        int valor_inactiu = 0; // No esté inactivo
        int valor_alta = 1; // Tenga el alta
        
        String condicion = CAMP_ADOPTAT + "=" + valor_adoptat + "," + CAMP_INACTIU + "=" + valor_inactiu + "," + CAMP_ALTA + "=" + valor_alta;
        
        if(any !=null && !any.isEmpty()){
            condicion += ", YEAR(" + CAMP_DATA_NAIX + ")=" + any;
        }
        if(tamany !=null && !tamany.isEmpty()){
            condicion += "," + CAMP_TAMANY + "=%" + tamany.toLowerCase() + "%";
        }
        
        //Consultar antes en la tabla de razas y se busca el ID en la tabla animales
        if(raza !=null && !raza.isEmpty()){
            List<Raza> razas = razaService.get(CAMP_DESC + "=%" + raza.toLowerCase() + "%");
            
            if(razas!= null && !razas.isEmpty()){
                condicion += ", " + CAMP_RAZA + "=" + razas.get(0).getIdRaza();
            }
        }
        //Consultar antes en la tabla de tipos de animal y se busca el ID en la tabla de animales
        if(especie !=null && !especie.isEmpty()){
            List<TipusAnimal> tipusAnimal = tipusAnimalService.get(CAMP_DESC + "=%" + especie.toLowerCase()+ "%");
            
            if(tipusAnimal!= null && !tipusAnimal.isEmpty()){
                condicion += ", " + CAMP_TIPUS_ANIMAL + "=" + tipusAnimal.get(0).getIdTipus();
            }
        }
        
        //SEXE debería de ser M o F para que lo busque bien.
        if(sexe!=null && !sexe.isEmpty()){
            int sexo = sexe.toUpperCase().equals("M") ? 0: sexe.toUpperCase().equals("F") ? 1:-1;
            
            condicion += ", " + CAMP_SEXE + "=" + sexo;
        }

        List<Animal> animals = animalService.getAND(condicion);
        
        return mapearAnimal(animals);
    }
    
    private List<AnimalBean> mapearAnimal(List<Animal> animals){
        //Mapear los animales a la clase AnimalBean
        List<AnimalBean> llistatOut = new ArrayList<AnimalBean>();
        for(Animal item:animals){
            
            //Si no s'ha filtrat per tipus d'animal, s'ha de consultar el tipus
            //Consultar antes en la tabla de tipos de animal y se busca el ID en la tabla de animales
            TipusAnimal tipusAnimalItem = (TipusAnimal) tipusAnimalService.getone(CAMP_ID_TIPUS + " = " + item.getTipusAnimal());             
            Raza razaItem = (Raza) razaService.getone(CAMP_ID_RAZA + " = " + item.getRaza());
            
            AnimalBean beanout = null;
           if(item.isIsAdoptat()){
               Adopcio adopcioItem = (Adopcio) adopcioService.getone(CAMP_ID_ANIMAL + " = " + item.getIdAnimal());
               beanout = new AnimalBean(item.getIdAnimal(), item.getNom(), item.getDataNaix().toString(), item.getSexe(), item.getTamany(), tipusAnimalItem.getDescripcio(), razaItem.getDescripcio(), item.isIsAlta() , item.getDataAlta().toString(), item.isIsVacunat(), item.isIsEsterlitzat(), item.isHasChip(), item.getNumChip(), item.getCreatedDate().toString(),  item.isIsAdoptat(), adopcioItem.getDataAdopcioString());
           }else{
               beanout = new AnimalBean(item.getIdAnimal(), item.getNom(), item.getDataNaix().toString(), item.getSexe(), item.getTamany(), tipusAnimalItem.getDescripcio(), razaItem.getDescripcio(), item.isIsAlta() , item.getDataAlta().toString(), item.isIsVacunat(), item.isIsEsterlitzat(), item.isHasChip(), item.getNumChip(), item.getCreatedDate().toString(),  item.isIsAdoptat(), null);
           }
           
           llistatOut.add(beanout);
        }
        
        return llistatOut;
    }
    
    @RequestMapping(value = "{codi}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Animal getbyCodi(@PathVariable String codi) {
        return (Animal) animalService.getone("idAnimal=" + codi);
    }
    
    /* fechas en formato ddMMyyyy */
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<AnimalBean> getbyDataAlta(@RequestParam String inici, @RequestParam (required = false) String fi) throws ParseException{
        SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
        
        Date fechaInicio = (Date) formato.parse(inici);
        inici = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio);
        
        Date fechaFin = null;
        
        if(fi != null && !fi.isEmpty()){
            fechaFin = (Date) formato.parse(fi);
            fi = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
        }else{
            Date actual = new Date();
            fi = new SimpleDateFormat("yyyy-MM-dd").format(actual);
        } 
        
        List<Animal> animals = animalService.get("between " + inici + "AND " + fi, CAMP_DATA_CREACIO);
        
        //Date createdDate = new Date((long)animals.*1000);
        /*TODO  cambiar fechas de tiempo unix a date */
       
        return mapearAnimal(animals);
        
    }
    
        /* fechas en formato ddMMyyyy */
    @RequestMapping(value = "/estadistiques", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EstadisicasBean getEstadistiquesbyData(@RequestParam String inici, @RequestParam (required = false) String fi) throws ParseException{
        SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
        
        Date fechaInicio = (Date) formato.parse(inici);
        inici = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio);
        
        Date fechaFin = null;
        
        if(fi != null && !fi.isEmpty()){
            fechaFin = (Date) formato.parse(fi);
            fi = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
        }else{
            Date actual = new Date();
            fi = new SimpleDateFormat("yyyy-MM-dd").format(actual);
        } 
        
        EstadisicasBean beanOut = new EstadisicasBean();
        
        //List<Animal> animals = animalService.get("between " + inici + "AND " + fi, CAMP_DATA_CREACIO);
        
        /*ALTAS:*/
        //Altas totales:
        
        List<Animal> animals = animalService.get("between " + inici + "AND " + fi, CAMP_DATA_CREACIO);
        beanOut.setAltes(animals.size());
        
       // List<Animal> animals = animalService.get("between " + inici + "AND " + fi, CAMP_DATA_CREACIO);
        
        

       
        return beanOut;
        
    }
   
   

 }
