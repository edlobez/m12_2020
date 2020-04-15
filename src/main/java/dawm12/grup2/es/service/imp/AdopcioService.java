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

import dawm12.grup2.es.domain.Adopcio;
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

@Component("adopcioService")
public class AdopcioService extends ServiceImp <Adopcio> implements Service <Adopcio>{

    @Autowired
    @Qualifier("adopcioImp")
    private MyRepository adopcionRepository;
    private String ADOPCIO = "adopcio";
    
    @Override
    protected List<Adopcio> getSeveral(String tipo_busqueda, String args, String campos) {
        return adopcionRepository.get(ADOPCIO, tipo_busqueda, args, campos);
    }

    @Override
    public Adopcio getone(String campos) {
        return (Adopcio) adopcionRepository.getone(ADOPCIO, campos);
    }

    @Override
    public Adopcio create(Adopcio type) {
        
        Adopcio a = null;
        
        try {
            a = (Adopcio) adopcionRepository.create(ADOPCIO,
                    "idanimal=" + type.getIdAnimal() + "," +
                    "idpersona=" + type.getIdPersona() + "," +
                    "dataadopcio="+type.getDataAdopcio());
        } catch (Exception ex) {
            return null;
        }
        return a;
        
        
    }

    @Override
    public Adopcio update(Adopcio type, String campo) {
        Adopcio a = null;
        try {
            a = (Adopcio) adopcionRepository.update(ADOPCIO, "idpersona="+type.getIdadopcio(), campo);
        } catch (Exception ex) {
            return null;
        }
        return a;
    }
    
}
