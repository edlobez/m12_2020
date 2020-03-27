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

import dawm12.grup2.es.domain.Imagen;
import dawm12.grup2.es.repository.MyRepository;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author edlobez
 */
@Repository
@Component("imagenImp")
public class ImagenImp extends MyRepositoryImp<Imagen> implements MyRepository<Imagen> {

    //public Imagen(String nombre, String tipo, long tanamo, byte[] pixel)
    @Override
    protected Imagen buildDomainFromResultSet(ResultSet rs) throws SQLException {

        return new Imagen(rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("tipo"),
                rs.getLong("tamano"),
                rs.getBytes("pixel"));
    }
}
