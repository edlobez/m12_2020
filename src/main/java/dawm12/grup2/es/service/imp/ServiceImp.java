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
package dawm12.grup2.es.service.imp;

/**
 *
 * @author z003says
 */
import java.util.List;
import dawm12.grup2.es.repository.MyRepository;

/**
 *
 * @author eduardo.lopez
 */
public abstract class ServiceImp <T>  {

   
    public List <T> getAll() {
        return this.getSeveral(null);
    }
   
    public List <T> getOR(String... campos) {
        return this.getSeveral(MyRepository.OR, campos);
    }
   
    public List <T> getAND(String... campos) {
        return this.getSeveral (MyRepository.AND, campos);
    }
   
    public List <T> get(String campos) {
        return this.getSeveral(null, campos);
    }
        
    
    protected abstract List<T> getSeveral(String tipo_busqueda, String... campos);
}