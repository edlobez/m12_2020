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

import dawm12.grup2.es.domain.Roles;
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

@Component("rolesService")
public class RolesService extends ServiceImp <Roles> implements Service <Roles> {
    
    @Autowired
    @Qualifier("rolesImp")
    private MyRepository rolesRepository;
    private static String ROLES="ROLES";

    @Override
    protected List<Roles> getSeveral(String tipo_busqueda, String args, String campos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Roles getone(String campos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Roles create(Roles type) {
        
        Roles r = null;
        try {
            r = (Roles) rolesRepository.create(ROLES, 
                    "USERNAME="+ type.getUsername() +","+
                    "ROLE="+type.getRole());
            
        } catch (Exception ex) {
            Logger.getLogger(RolesService.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return r;
        
    }

    @Override
    public Roles update(Roles type, String campos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
