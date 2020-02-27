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
package dawm12.grup2.es.myExceptions;

/**
 *
 * @author edlobez
 */
public class MyException extends Exception {    
   
    public static int ERROR_UPDATE_1=0; 
    private int codigo;    
    private static String [] msg = {
        "Error al crear consulta update, faltan campos a actualizar"
    };   
       
    
    public MyException (int codigo) {
        super();
        this.codigo = codigo;
    }

    @Override
    public String getMessage() {
        return msg[this.codigo];
    }
    
    
    
    
}
