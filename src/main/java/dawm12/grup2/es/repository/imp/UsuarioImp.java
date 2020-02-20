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
package dawm12.grup2.es.repository.imp;

import dawm12.grup2.es.domain.Usuarios;
import dawm12.grup2.es.repository.MyRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author z003says
 */

@Repository
@Component("usuarioImp")
public class UsuarioImp extends MyRepositoryImp <Usuarios> implements MyRepository <Usuarios> {

    @Override
    protected Usuarios buildDomainFromResultSet(ResultSet rs) throws SQLException {
        return new Usuarios (
                rs.getString("username"),
                "",
                rs.getBoolean("enabled"),
                rs.getString("nombre"),
                rs.getString("apellido1"),
                rs.getString("apellido2"),
                rs.getString("email")
        );
    }
    
}
