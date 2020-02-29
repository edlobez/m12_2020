/*
 * Copyright (C) 2020 Sonia
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
package dawm12.grup2.es.domain;

import java.util.Date;


/**
 *
 * @author Sonia
 */
public class Comentari {
    
    private int idComentari;
    private String descripcio;
    private int idAnimal;
    private Date createdDate;
    private String createdUser;

    public Comentari(int idComentari, String descripcio, int idAnimal, Date createdDate, String createdUser) {
        this.idComentari = idComentari;
        this.descripcio = descripcio;
        this.idAnimal = idAnimal;
        this.createdUser = createdUser;
        this.createdDate = createdDate;
    }

    public int getIdComentari() {
        return idComentari;
    }

    public void setIdComentari(int idComentari) {
        this.idComentari = idComentari;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
}
