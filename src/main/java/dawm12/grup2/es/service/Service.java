/*
 * Copyright (C) 2020 z003says
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
package dawm12.grup2.es.service;

/**
 *
 * @author z003says
 */
import java.util.List;

/**
 *
 * @author edlobez-vbox
 * @param <T>
 */
public interface Service <T> {    
    
    /**
     * Retorna una lista de elementos con todos los elementos
     * que contiene una tabla de la base de datos.
     * @return lista con todos los elementos de la tabla
     */
    List<T> getAll ();
    
    /**
     * Retorna una lista con todos los elementos de una tabla de la
     * base de datos. La lista la retornará en función de los criterios de
     * ordenación indicados en el parámetro args.
     * Estos parámetros pueden ser "ORDER BY XXX" "GROUP BY" "LIMIT 1" 
     * "LIMIT 1 OFFSET 2" "BETWEEN x AND Y   
     * @param args criterios de ordenación/búsqueda
     * @return la lista con todos los elementos de la tabla
     */
    List<T> getAll (String args);   
    
    /**
     * Retorna una lista con todos los elementos de la tabla que cumplan la 
     * condicion OR de los campos especificados
     * @param campos pares de nombre=valor separados por coma (,)
     * @return la lista de todos los elementos de la tabla que cumplen la
     * condición
     */
    List<T> getOR (String campos);
     /**
     * Retorna una lista con todos los elementos de la tabla que cumplan la 
     * condicion OR de los campos especificados. La lista la retorna en función
     * de los criterios indicados en el parámetro args
     * @param args criterios de ordenación/búsqueda
     * @param campos pares de nombre=valor separados por coma (,)
     * @return la lista de todos los elementos de la tabla que cumplen la
     * condición.
     */
    List<T> getOR (String args, String campos);
    /**
     * Retorna una lista con todos los elementos de la tabla que cumplan la 
     * condicion AND de los campos especificados
     * @param campos pares de nombre=valor separados por coma (,)
     * @return la lista de todos los elementos de la tabla que cumplen la
     * condición
     */
    List<T> getAND (String campos);
    /**
     * Retorna una lista con todos los elementos de la tabla que cumplan la 
     * condicion AND de los campos especificados. La lista la retorna en función
     * de los criterios indicados en el parámetro args
     * @param args criterios de ordenación/búsqueda
     * @param campos pares de nombre=valor separados por coma (,)
     * @return la lista de todos los elementos de la tabla que cumplen la
     * condición.
     */
    List<T> getAND (String args, String campos);
    /**
     * Retorna todos los elementos de una tabla que cumplan la condición del
     * campo especificado en el parámetro campo
     * @param campo campo par nombre=valor a buscar en la tabla
     * @return la lista de todos los elementos de la tabla que cumplan la 
     * condición
     */
    List<T> get (String campo);
    /**
     * Retorna todos los elementos de la tabla que cumplan la condición del 
     * campo especificado en el parámetro campo y segun el criterio del 
     * indicado en el parámetro args
     * @param args criterios de ordenación/agrupación
     * @param campos par nombre=valor a buscar en la tabla
     * @return la lista de todos los elementos de la tabla que cumplan la 
     * condición agurpados/ordenados
     */
    List<T> get (String args, String campos);
    /**
     * Retorna un único elementos de la tabla que cumpla la condición del campo
     * especificado en el parámetro campo.
     * @param campos par nombre=valor a buscar en la tabla
     * @return 
     */
    T getone (String campos);
    /**
     * Crea un nuevo elemento en la base de datos
     * @param type el elemento a crear
     * @return el elemento creado si ha tenido éxito, null en caso contrario.
     */
    public T create (T type);
    /**
     * Modifica un campo de la tabla con los valores del elemento pasado por 
     * parámetro
     * @param type el elemento a modificar con los valores nuevos
     * @param campo criterio de búsqueda para modificar el registro
     * @return el elemento modificado si ha tenido éxito, null en caso contrario.
     */
    public T update (T type, String campo);
    
}