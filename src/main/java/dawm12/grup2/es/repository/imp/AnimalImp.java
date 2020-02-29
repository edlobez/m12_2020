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

import dawm12.grup2.es.domain.Animal;
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
@Component("animalImp")
public class AnimalImp extends MyRepositoryImp <Animal> implements MyRepository <Animal> {

    //    public Animal(int idAnimal, String nom, Date dataNaix, char sexe, String tamany, int tipusAnimal, int raza, boolean isAlta, 
    //Date dataAlta, boolean isAdoptat, boolean isVacunat, boolean isEsterlitzat, boolean hasChip, int numChip, 
    //String vetAssignat, Date createdDate, String createdUser) {

    @Override
    protected Animal buildDomainFromResultSet(ResultSet rs) throws SQLException {
        Animal an = new Animal (
                rs.getInt("idAnimal"),
                rs.getString("nom"),
                rs.getDate("datanaix"),
                rs.getString("sexe"),
                rs.getString("tamany"),
                rs.getInt("tipusanimal"),
                rs.getInt("raza"),
                rs.getBoolean("isalta"),
                rs.getDate("dataalta"),
                rs.getBoolean("isadoptat"),
                rs.getBoolean("isvacunat"),
                rs.getBoolean("isesterilitzat"),
                rs.getBoolean("haschip"),
                rs.getInt("numChip"),
                rs.getString("vetassignat"),
                rs.getDate("createddate"),
                rs.getString("createduser"),
                rs.getString("imatge")
                
        );
        return an;
    }    
    
}
