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

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author Sonia
 */
public class Persona {
    
    private int idpersona;
    
    @NotNull
    @Size(min=3, max=30, message="Camp nom no pot estar buit")
    private String nom;
    
    @NotNull
    @Size(min=3, max=30, message="Camp cognom no pot estar buit")
    private String cognom1;
    
    @Size(max=30, message="Cognom 2 massa llarg (max 30 caracters)")
    private String cognom2;
    
    @NotEmpty (message="Camp email no pot estar buit")
    @Email(message = "E-mail incorrecte")
    @Size(max=30, message="Email massa llarg (max 30 caracters)")
    private String email;
    
    @NotNull (message="Camp teléfon no pot estar buit")
    @Max(value=999999999, message="Teléfon incorrecte")
    private int telefon;
    
    @NotEmpty (message="Camp direcció no pot estar buit")
    @Size(max=30, message="La direcció es massa llarga (max 50 caracters)")
    private String direccio;

    public Persona(String nom, String cognom1, String cognom2, String email, int telefon, String direccio) {
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.email = email;
        this.telefon = telefon;
        this.direccio = direccio;
    }

    public Persona(int idpersona, String nom, String cognom1, String cognom2, String email, int telefon, String direccio) {
        this.idpersona = idpersona;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.email = email;
        this.telefon = telefon;
        this.direccio = direccio;
    }
    
    
    
    
    public Persona () {}

    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }

    
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    @Override
    public String toString() {
        return "Persona{" + "nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + ", email=" + email + ", telefon=" + telefon + ", direccio=" + direccio + '}';
    }

}
