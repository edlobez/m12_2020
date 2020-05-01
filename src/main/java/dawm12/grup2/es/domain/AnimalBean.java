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

/**
 *
 * @author Sonia
 */
public class AnimalBean {
    
    private int idAnimal;
    private String nom;
    private String dataNaix;
    private String sexe;
    private String tamany;
    private String tipusAnimal; //Será la descripció del tipus d'animal
    private String raza; //Será la descripció de la raça
    private boolean isAlta;
    private String dataAlta;
    
    private boolean isVacunat;
    private boolean isEsterlitzat;
    private boolean hasXip;
    private int numXip;
    //private boolean inactiu;
    private String createdDate;
    //private String createdUser;
    //private String imatge;
    
    // Guardamos el literal de la raza  tipo animal veterinario y fecha de adopcion
    // ...para el json del listado
    //private String tAnimal;
    //private String laRaza;
    //private String veterinari;
    private boolean isAdoptat;
    private String adopcioDate;
    //private String nomAdoptante;

    public AnimalBean(int idAnimal, String nom, String dataNaix, String sexe, String tamany, String tipusAnimal, String raza, boolean isAlta, String dataAlta, boolean isVacunat, boolean isEsterlitzat, boolean hasXip, int numXip, String createdDate, boolean isAdoptat, String adopcioDate) {
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
        this.hasXip = hasXip;
        this.numXip = numXip;
        this.createdDate = createdDate;
        this.adopcioDate = adopcioDate;
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

    public String getDataNaix() {
        return dataNaix;
    }

    public void setDataNaix(String dataNaix) {
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

    public String getTipusAnimal() {
        return tipusAnimal;
    }

    public void setTipusAnimal(String tipusAnimal) {
        this.tipusAnimal = tipusAnimal;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public boolean isIsAlta() {
        return isAlta;
    }

    public void setIsAlta(boolean isAlta) {
        this.isAlta = isAlta;
    }

    public String getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(String dataAlta) {
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

    public boolean isHasXip() {
        return hasXip;
    }

    public void setHasXip(boolean hasXip) {
        this.hasXip = hasXip;
    }

    public int getNumXip() {
        return numXip;
    }

    public void setNumXip(int numXip) {
        this.numXip = numXip;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAdopcioDate() {
        return adopcioDate;
    }

    public void setAdopcioDate(String adopcioDate) {
        this.adopcioDate = adopcioDate;
    }

  
}
