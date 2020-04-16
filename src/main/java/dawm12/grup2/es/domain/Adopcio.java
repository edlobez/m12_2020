
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

import java.sql.Date;


/**
 *
 * @author Sonia
 */
public class Adopcio {
    
    private int idadopcio;
    private int idAnimal;
    private int idPersona;
    private Date dataAdopcio;
    private String dataAdopcioString;

    public Adopcio(int idadopcio, int idAnimal, int idPersona, Date dataAdopcio) {
        this.idadopcio = idadopcio;
        this.idAnimal = idAnimal;
        this.idPersona = idPersona;
        this.dataAdopcio = dataAdopcio;
        this.dataAdopcioString = dataAdopcio.toString();
    }

    public Adopcio(int idAnimal, int idPersona, Date dataAdopcio) {
        this.idAnimal = idAnimal;
        this.idPersona = idPersona;
        this.dataAdopcio = dataAdopcio;
        this.dataAdopcioString = dataAdopcio.toString();
    }
    
    

    public int getIdadopcio() {
        return idadopcio;
    }

    public void setIdadopcio(int idadopcio) {
        this.idadopcio = idadopcio;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Date getDataAdopcio() {
        return dataAdopcio;
    }

    public void setDataAdopcio(Date dataAdopcio) {
        this.dataAdopcio = dataAdopcio;
    }

    public String getDataAdopcioString() {
        return dataAdopcioString;
    }

    public void setDataAdopcioString(String dataAdopcioString) {
        this.dataAdopcioString = dataAdopcioString;
    }

    @Override
    public String toString() {
        return "Adopcio{" + "idadopcio=" + idadopcio + ", idAnimal=" + idAnimal + ", idPersona=" + idPersona + ", dataAdopcioString=" + dataAdopcioString + '}';
    }
    
    
    
}
