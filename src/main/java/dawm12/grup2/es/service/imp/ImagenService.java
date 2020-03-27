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

import dawm12.grup2.es.domain.Imagen;
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
@Component("imagenService")
public class ImagenService extends ServiceImp <Imagen> implements Service <Imagen> {

    @Autowired @Qualifier("imagenImp")
    private MyRepository imagenRepository;
    private String IMAGEN = "imagen";
    
    @Override
    protected List<Imagen> getSeveral(String tipo_busqueda, String args, String campos) {
        return imagenRepository.get(IMAGEN, tipo_busqueda, args, campos);
    }

    @Override
    public Imagen getone(String campos) {
        return (Imagen) imagenRepository.getone(IMAGEN, campos);
    }

    @Override
    public Imagen create(Imagen type) {
        
        Imagen i = null;
        try {
            i = (Imagen) imagenRepository.create(IMAGEN, 
                    "nombre="+type.getNombre() + "," 
                    + "tipo="+type.getTipo()+ "," 
                    + "tamano=" + type.getTamano() + "," 
                    + "pixel=" + type.getPixel());
        }catch (Exception e) {
            return null;
        }
        return i;

    }

    @Override
    public Imagen update(Imagen type, String campo) {
        Imagen i = null;
        try {
            i = (Imagen) imagenRepository.update(IMAGEN, "id="+type.getId(), campo);
        } catch (Exception ex) {
            return null;
        }
        return i;

    }
    
}
