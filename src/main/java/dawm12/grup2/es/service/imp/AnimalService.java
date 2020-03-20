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
package dawm12.grup2.es.service.imp;

import dawm12.grup2.es.domain.Animal;
import dawm12.grup2.es.repository.MyRepository;
import dawm12.grup2.es.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author edlobez
 */
@Component("animalService")
public class AnimalService extends ServiceImp <Animal> implements Service <Animal> {
    
    @Autowired
    @Qualifier("animalImp")
    private MyRepository animalRepository;
    private static String ANIMAL="animal";

    @Override
    protected List<Animal> getSeveral(String tipo_busqueda, String args, String campos) {
        return animalRepository.get(ANIMAL, tipo_busqueda, args, campos);
    }

    @Override
    public Animal getone(String campos) {
        return (Animal) animalRepository.getone(ANIMAL, campos);
    }

    
//    public Animal(int idAnimal, String nom, Date dataNaix, String sexe, 
    //String tamany, int tipusAnimal, int raza, boolean isAlta,
  //          Date dataAlta, boolean isAdoptat, boolean isVacunat, 
  //  boolean isEsterlitzat, boolean hasChip, int numChip, String vetAssignat, Date createdDate, String createdUser, String imatge) {
//
    @Override
    public Animal create(Animal type) {
        Animal a = null;
        try {
            a = (Animal) animalRepository.create(ANIMAL, 
                    "nom="+type.getNom()+","+
                    "datanaix="+type.getDataNaix()+","+
                    "sexe="+type.getSexe()+","+
                    "tamany="+type.getTamany()+","+
                    "tipusAnimal="+type.getTipusAnimal()+","+
                    "raza="+type.getRaza()+","+
                    "isalta="+((type.isIsAlta())?1:0)+","+
                    "dataalta="+type.getDataAlta()+","+
                    "isadoptat="+((type.isIsAdoptat())?1:0)+","+
                    "isvacunat="+((type.isIsVacunat())?1:0)+","+
                    "isesterilitzat="+((type.isIsEsterlitzat())?1:0)+","+
                    "haschip="+((type.isHasChip())?1:0)+","+
                    "vetassignat="+type.getVetAssignat()+","+
                    "inactiu="+((type.isInactiu())?1:0)+","+
                    "createddate="+type.getCreatedDate()+","+
                    "createduser="+type.getCreatedUser()+","+
                    "imatge="+type.getImatge()                    
                    );
        }catch (Exception e) {
            return a;
        }
        return a;
    }

    @Override
    public Animal update(Animal type, String campos) {
        Animal a = null;
        try {
            a = (Animal) animalRepository.update(ANIMAL, "idanimal="+type.getIdAnimal(), campos);
        } catch (Exception ex) {
            return null;
        }
        return a;
    }
    
}
