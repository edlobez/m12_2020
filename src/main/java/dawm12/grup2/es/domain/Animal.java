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
import javax.validation.constraints.NotNull;


/**
 *
 * @author Sonia
 */
public class Animal {
    
    private int idAnimal;
    private String nom;
    
    @NotNull    
    private Date dataNaix;
    
    private String sexe;
    private String tamany;
    private int tipusAnimal;
    private int raza;
    private boolean isAlta;
    private Date dataAlta;
    private boolean isAdoptat;
    private boolean isVacunat;
    private boolean isEsterlitzat;
    private boolean hasChip;
    private int numChip;
    private String vetAssignat;
    private boolean inactiu;
    private Date createdDate;
    private String createdUser;
    private String imatge;
    
    // Guardamos el literal de la raza y tipo animal
    private String tAnimal;
    private String laRaza;

    public Animal(int idAnimal, String nom, Date dataNaix, String sexe, String tamany, int tipusAnimal, int raza, boolean isAlta, Date dataAlta, boolean isAdoptat, boolean isVacunat, boolean isEsterlitzat, boolean hasChip, int numChip, String vetAssignat, boolean inactiu, Date createdDate, String createdUser, String imatge) {
        this.idAnimal = idAnimal;
        this.nom = nom;
        this.dataNaix = dataNaix;
        this.sexe = sexe;
        this.tamany = tamany;
        this.tipusAnimal = tipusAnimal;
        this.raza = raza;
        this.isAlta = isAlta;
        this.dataAlta = dataAlta;
        this.isAdoptat = isAdoptat;
        this.isVacunat = isVacunat;
        this.isEsterlitzat = isEsterlitzat;
        this.hasChip = hasChip;
        this.numChip = numChip;
        this.vetAssignat = vetAssignat;
        this.inactiu = inactiu;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
        this.imatge = imatge;
    }

    public Animal() {
    }
    
    

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDataNaix() {
        return dataNaix;
    }

    public void setDataNaix(Date dataNaix) {
        this.dataNaix = dataNaix;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTamany() {
        return tamany;
    }

    public void setTamany(String tamany) {
        this.tamany = tamany;
    }

    public int getTipusAnimal() {
        return tipusAnimal;
    }

    public void setTipusAnimal(int tipusAnimal) {
        this.tipusAnimal = tipusAnimal;
    }

    public int getRaza() {
        return raza;
    }

    public void setRaza(int raza) {
        this.raza = raza;
    }

    public boolean isIsAlta() {
        return isAlta;
    }

    public void setIsAlta(boolean isAlta) {
        this.isAlta = isAlta;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public boolean isIsAdoptat() {
        return isAdoptat;
    }

    public void setIsAdoptat(boolean isAdoptat) {
        this.isAdoptat = isAdoptat;
    }

    public boolean isIsVacunat() {
        return isVacunat;
    }

    public void setIsVacunat(boolean isVacunat) {
        this.isVacunat = isVacunat;
    }

    public boolean isIsEsterlitzat() {
        return isEsterlitzat;
    }

    public void setIsEsterlitzat(boolean isEsterlitzat) {
        this.isEsterlitzat = isEsterlitzat;
    }

    public boolean isHasChip() {
        return hasChip;
    }

    public void setHasChip(boolean hasChip) {
        this.hasChip = hasChip;
    }

    public int getNumChip() {
        return numChip;
    }

    public void setNumChip(int numChip) {
        this.numChip = numChip;
    }

    public String getVetAssignat() {
        return vetAssignat;
    }

    public void setVetAssignat(String vetAssignat) {
        this.vetAssignat = vetAssignat;
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

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public boolean isInactiu() {
        return inactiu;
    }

    public void setInactiu(boolean inactiu) {
        this.inactiu = inactiu;
    }

    public String gettAnimal() {
        return tAnimal;
    }

    public void settAnimal(String tAnimal) {
        this.tAnimal = tAnimal;
    }

    public String getLaRaza() {
        return laRaza;
    }

    public void setLaRaza(String laRaza) {
        this.laRaza = laRaza;
    }

    @Override
    public String toString() {
        return "Animal{" + "idAnimal=" + idAnimal + ", nom=" + nom + ", dataNaix=" + dataNaix + ", sexe=" + sexe + ", tamany=" + tamany + ", tipusAnimal=" + tipusAnimal + ", raza=" + raza + ", isAlta=" + isAlta + ", dataAlta=" + dataAlta + ", isAdoptat=" + isAdoptat + ", isVacunat=" + isVacunat + ", isEsterlitzat=" + isEsterlitzat + ", hasChip=" + hasChip + ", numChip=" + numChip + ", vetAssignat=" + vetAssignat + ", inactiu=" + inactiu + ", createdDate=" + createdDate + ", createdUser=" + createdUser + ", imatge=" + imatge + ", tAnimal=" + tAnimal + ", laRaza=" + laRaza + '}';
    }
   
       
}
