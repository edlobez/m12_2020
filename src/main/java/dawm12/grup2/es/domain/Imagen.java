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

import java.io.InputStream;
import java.sql.Blob;

/**
 *
 * @author edlobez
 */
public class Imagen {
    
    private int id; //Id del animal al que pertenece
    private String nombre; // Nombre de la imagen max 100 carateres
    private String tipo;  // tipo de fichero
    private long tamano; // tamano de la imagen
    private byte[] pixel; // blob
   

    public Imagen() {
    }

    public Imagen(String nombre, String tipo, long tamano, byte[] pixel) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.tamano = tamano;
        this.pixel = pixel;
    }

    public Imagen(int id, String nombre, String tipo, long tamano, byte[] pixel) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.tamano = tamano;
        this.pixel = pixel;
    }

    
    public int getId() {
        return id;
    }
/*
    public void setId(int id) {
        this.id = id;
    }
*/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getTamano() {
        return tamano;
    }

    public void setTamano(long tamano) {
        this.tamano = tamano;
    }

    public byte[] getPixel() {
        return pixel;
    }

    public void setPixel(byte[] pixel) {
        this.pixel = pixel;
    }

    @Override
    public String toString() {
        return "Imagen{" + "id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", tamano=" + tamano + ", pixel=" + pixel + '}';
    }
    
    
    
}
