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
 */
public interface Service <T> {    
    
    List<T> getAll ();
    List<T> getAll (String args);    
    List<T> getOR (String campos);
    List<T> getOR (String args, String campos);
    List<T> getAND (String campos);
    List<T> getAND (String args, String campos);
    List<T> get (String campos);
    List<T> get (String args, String campos);
    T getone (String campos);
    public T create (T type);
    public T update (T type, String campos);
    
}