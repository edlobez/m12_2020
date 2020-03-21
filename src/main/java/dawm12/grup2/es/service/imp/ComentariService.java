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

import dawm12.grup2.es.domain.Comentari;
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
 
@Component("comentariService")
public class ComentariService extends ServiceImp <Comentari> implements Service <Comentari> {
    
    @Autowired
    @Qualifier("comentariImp")
    private MyRepository comentariRepository;
    private static String COMENTARI="comentari";

    @Override
    protected List<Comentari> getSeveral(String tipo_busqueda, String args, String campos) {
        return comentariRepository.get(COMENTARI,tipo_busqueda, args, campos);
    }

    @Override
    public Comentari getone(String campos) {
        return (Comentari) comentariRepository.getone(COMENTARI, campos);
    }

    @Override
    public Comentari create(Comentari type) {
        
        Comentari c = null;
        try {
            c = (Comentari) comentariRepository.create(COMENTARI, 
                    "descripcio=" + type.getDescripcio()+","+
                    "idanimal="+type.getIdAnimal()+","+
                    "createddate="+type.getCreatedDate()+","+
                    "createduser="+type.getCreatedUser());
        }catch (Exception e) {
            System.out.println("Error al crear el comentario en comentariService");
            return null;
        }
        return c;
    }

    @Override
    public Comentari update(Comentari type, String campo) {
        
        Comentari c = null;
        try {
            c = (Comentari) comentariRepository.update(COMENTARI, "idcomentari="+type.getIdComentari(), campo);            
        }catch (Exception e) {
            return null;
        }
        return c;
    }
    
}
