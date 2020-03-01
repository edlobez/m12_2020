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

import dawm12.grup2.es.domain.Raza;
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
@Component("razaService")
public class RazaService extends ServiceImp <Raza> implements Service <Raza> {
    
    @Autowired
    @Qualifier("razaImp")
    private MyRepository razaRepository;
    private static String RAZA="raza";

    @Override
    protected List<Raza> getSeveral(String tipo_busqueda, String args, String campos) {
        return razaRepository.get(RAZA, tipo_busqueda, args, campos);
    }

    @Override
    public Raza getone(String campos) {
        return (Raza) razaRepository.getone(RAZA, campos);
    }

    @Override
    public Raza create(Raza type) {
        
        Raza z = null;
        try {
            z = (Raza) razaRepository.create(RAZA, "descripcio="+type.getDescripcio());
        } catch (Exception e) {
            return null;
        }
        return z;
        
    }

    @Override
    public Raza update(Raza type, String campo) {
        
        Raza z = null;
        try {
            z = (Raza) razaRepository.update(RAZA, "idraza="+type.getIdRaza(), campo);
        } catch (Exception e) {
            return null;
        }
        return z;
        
    }
    
    
}
