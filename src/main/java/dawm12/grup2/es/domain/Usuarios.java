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
package dawm12.grup2.es.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author edlobez
 */
public class Usuarios {
    
    @NotNull
    @Size(min=3, max=8, message="L'ID d'usuari no pot estar buit, ha de tenir entre 3 y 8 caracters")
    private String username;
    
    //@Size(min=4, message="La contrasenya ha de tenir un mínim de 4 caracters")
    //La validación del password la hacemos en el controller
    private String password;
    
    private boolean enabled;
    private boolean changePass;
    
    @NotNull
    @Size(min=3, max=30, message="Camp nom no pot estar buit")
    private String nombre;
    
    @NotNull
    @Size(min=3, max=30, message="Camp cognom no pot estar buit")
    private String apellido1;
    
    @Size(max=30, message="Cognom 2 massa llarg (max 30 caracters)")
    private String apellido2;
    
    @NotEmpty (message="Camp email no pot estar buit")
    @Email(message = "E-mail incorrecte")
    @Size(max=30, message="Email massa llarg (max 30 caracters)")
    private String email;
    
    private int rol;
    private int tipusAnimal;
    
    // Elementos para el Json de listado
    private String tAnimal;
    private String srol;

    public Usuarios(String username,String password, boolean enabled, boolean changePass, String nombre, String apellido1, String apellido2, String email, int rol, int tipusAnimal) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.changePass = changePass;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.rol = rol;
        this.tipusAnimal = tipusAnimal;
    }

    public Usuarios() {
    }
    
    public Usuarios ( Usuarios usr ) {
        this.username = usr.getUsername();
        this.password = usr.getPassword();
        this.enabled = usr.isEnabled();
        this.enabled = usr.isChangePass();
        this.nombre = usr.getNombre();
        this.apellido1 = usr.getApellido1();
        this.apellido2 = usr.getApellido2();
        this.email = usr.getEmail(); 
        this.rol = usr.getRol();
        this.tipusAnimal = usr.getTipusAnimal();       
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isChangePass() {
        return changePass;
    }

    public void setChangePass(boolean changePass) {
        this.changePass = changePass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getRol() {
        return rol;
    }

    public void setEmail(int rol) {
        this.rol = rol;
    }

    public int getTipusAnimal() {
        return tipusAnimal;
    }

    public void setTipusAnimal(int tipusAnimal) {
        this.tipusAnimal = tipusAnimal;
    }

    public String gettAnimal() {
        return tAnimal;
    }

    public void settAnimal(String tAnimal) {
        this.tAnimal = tAnimal;
    }

    public String getSrol() {
        return srol;
    }

    public void setSrol(String Srol) {
        this.srol = Srol;
    }
    
    

  /*  @Override
    public String toString() {
        return "Usuarios{" + "username=" + username + ", password=" + password + ", enabled=" + enabled + ", changePass=" + changePass + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", email=" + email + ", rol=" + rol + ", tipusAnimal=" + tipusAnimal + '}';
    }*/

    @Override
    public String toString() {
        return "Usuarios{" + "username=" + username + ", password=" + password + ", enabled=" + enabled + ", changePass=" + changePass + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", email=" + email + ", rol=" + rol + ", tipusAnimal=" + tipusAnimal + ", tAnimal=" + tAnimal + '}';
    }
    
    

}
