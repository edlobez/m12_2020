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

import dawm12.grup2.es.domain.TipusAnimal;
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
@Component("tipusAnimalService")
public class TipusAnimalService extends ServiceImp <TipusAnimal> implements Service <TipusAnimal> {
    
    @Autowired
    @Qualifier("tipusAnimalImp")
    private MyRepository tipusAnimalRepository;
    private static String TIPUS_ANIMAL = "tipusanimal";

    @Override
    protected List<TipusAnimal> getSeveral(String tipo_busqueda, String args, String campos) {
        return tipusAnimalRepository.get(TIPUS_ANIMAL, tipo_busqueda, args, campos);
    }    

    @Override
    public TipusAnimal getone(String campos) {
        return (TipusAnimal) tipusAnimalRepository.getone(TIPUS_ANIMAL, campos);
    }

    @Override
    public TipusAnimal create(TipusAnimal type) {
        
        TipusAnimal t = null;
        try {
            t = (TipusAnimal) tipusAnimalRepository.create(TIPUS_ANIMAL, "descripcio="+type.getDescripcio());
        }catch (Exception e) {
            return null;
        }
        return t;
    }

    @Override
    public TipusAnimal update(TipusAnimal type, String campo) {
        
        TipusAnimal t = null;
        try {
            t = (TipusAnimal) tipusAnimalRepository.update(TIPUS_ANIMAL, "idtipus="+type.getIdTipus(), campo);
        } catch (Exception e) {
            return null;
        }
        return t;
    }
    
}
