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
package dawm12.grup2.es.repository.imp;

import dawm12.grup2.es.domain.Roles;
import dawm12.grup2.es.repository.MyRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author edlobez
 */

@Repository
@Component("rolesImp")
public class RolesImp extends MyRepositoryImp <Roles> implements MyRepository <Roles> {

    @Override
    protected Roles buildDomainFromResultSet(ResultSet rs) throws SQLException {
        return new Roles (rs.getString("username"), rs.getString("role"));
    }
    
}
