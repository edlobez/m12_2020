/*
 * Copyright (C) 2020 DAW_M12_grup2
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
package dawm12.grup2.es.repository;

import java.util.List;

/**
 *
 * @author DAW_M12_grup2
 */

public interface MyRepository <T> {
    
    public static String AND = "AND";
    public static String OR = "OR";
    
    public List<T> get (String nomTabla, String tipo_busqueda, String args, String... campos);
   // public List<T> get (String nomTabla, String tipo_busqueda, String... campos);
    public T getone (String nomTabla, String... campos);
    public T create (String nomTabla, String... campos) throws Exception;
    public T update (String nombTabla, String condicion, String... campos) throws Exception;
    public void delete (T t);
}
    

