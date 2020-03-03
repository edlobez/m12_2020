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

import dawm12.grup2.es.domain.Accesos;
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
@Component("accesosService")
public class AccesosService extends ServiceImp <Accesos> implements Service <Accesos> {
    
    @Autowired
    @Qualifier("accesosImp")
    private MyRepository accesosRepository;
    private String ACCESOS="accesos";

    @Override
    protected List<Accesos> getSeveral(String tipo_busqueda, String args, String campos) {
        return accesosRepository.get(ACCESOS, tipo_busqueda, args, campos);
    }

    @Override
    public Accesos getone(String campos) {
        return (Accesos) accesosRepository.getone(ACCESOS, campos);
    }

    @Override
    public Accesos create(Accesos type) {
        
        Accesos ac = null;
        try {
            ac = (Accesos) accesosRepository.create(ACCESOS, "accesos="+type.getDate()+",username="+type.getUsername());
        }catch (Exception e) {
            return null;
        }
        return ac;
        
    }

    @Override
    public Accesos update(Accesos type, String campo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
