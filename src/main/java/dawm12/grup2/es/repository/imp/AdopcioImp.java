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

import dawm12.grup2.es.domain.Adopcio;
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
@Component("adopcioImp")
public class AdopcioImp extends MyRepositoryImp <Adopcio> implements MyRepository <Adopcio>{

    @Override
    protected Adopcio buildDomainFromResultSet(ResultSet rs) throws SQLException {
        return new Adopcio (rs.getInt("idadopcio"), rs.getInt("idanimal"), rs.getInt("idpersona"), rs.getDate("dataadopcio"));
    }
    
}
