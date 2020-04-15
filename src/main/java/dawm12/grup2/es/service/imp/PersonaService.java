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

import dawm12.grup2.es.domain.Persona;
import dawm12.grup2.es.repository.MyRepository;
import dawm12.grup2.es.service.Service;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author edlobez
 */

@Component("personaService")
public class PersonaService extends ServiceImp <Persona> implements Service <Persona>{

    @Autowired
    @Qualifier("personaImp")
    private MyRepository personaRepository;
    private String PERSONA = "persona";
    
    @Override
    protected List<Persona> getSeveral(String tipo_busqueda, String args, String campos) {
        return personaRepository.get(PERSONA, tipo_busqueda, args, campos);
    }

    @Override
    public Persona getone(String campos) {
        return (Persona) personaRepository.getone(PERSONA, campos);
    }

    @Override
    public Persona create(Persona type) {
        
        Persona p = null;
        
        try {
            p = (Persona) personaRepository.create(PERSONA,
                    "nom=" +type.getNom() + "," +
                    "cognom1=" + type.getCognom1() + "," +
                    "cognom2=" + type.getCognom2() + "," +
                    "email=" + type.getEmail() + "," +
                    "telefon=" + type.getTelefon() + "," +
                    "direccio="+type.getDireccio());
        } catch (Exception ex) {
            return p;
        }
        return p;
    }

    @Override
    public Persona update(Persona type, String campo) {
        Persona p = null;
        try {
            p = (Persona) personaRepository.update(PERSONA, "idpersona="+type.getIdpersona(), campo);
        } catch (Exception ex) {
            return null;
        }
        return p;
    }
    
}
