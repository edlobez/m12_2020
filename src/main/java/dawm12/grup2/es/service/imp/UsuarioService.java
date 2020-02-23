/*
 * Copyright (C) 2020 z003says
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

import dawm12.grup2.es.PasswordEncoderGenerator;
import dawm12.grup2.es.domain.Usuarios;
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
 * @author z003says
 */

@Component("usuarioService")
public class UsuarioService extends ServiceImp <Usuarios> implements Service <Usuarios>{

    @Autowired
    @Qualifier("usuarioImp")
    private MyRepository usuarioRepository;
    private static String USUARIOS="USUARIOS";
    
    @Override
    protected List<Usuarios> getSeveral(String tipo_busqueda, String args, String campos) {
        return usuarioRepository.get(USUARIOS, tipo_busqueda, args, campos);
    }

    @Override
    public Usuarios getone(String campos) {
        return (Usuarios) usuarioRepository.getone(USUARIOS, campos);
    }

    @Override
    public Usuarios create(Usuarios type) {
        Usuarios u = null;
        try {
            u = (Usuarios) usuarioRepository.create(USUARIOS, 
                         "username="+type.getUsername() +","+
                         "password="+PasswordEncoderGenerator.passwordGenerator(type.getPassword())+","+
                         "enabled="+ ((type.isEnabled())?1:0)+","+
                         "nombre="+type.getNombre()+","+
                         "apellido1="+type.getApellido1()+","+
                         "apellido2="+type.getApellido2()+","+
                         "email="+type.getEmail());
        } catch (Exception ex) {
            return u;
          //  Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return u;
    }

    @Override
    public Usuarios update(Usuarios type, String campos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
