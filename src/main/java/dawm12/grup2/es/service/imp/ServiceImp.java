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
        return this.getSeveral(null, null, null);
    }
    public List <T> getAll(String args) {
        return this.getSeveral(null, args, null);
    }
   
    public List <T> getOR(String campos) {
        return this.getSeveral(MyRepository.OR, null, campos);
    }
    public List <T> getOR(String args, String campos) {
        return this.getSeveral(MyRepository.OR, args, campos);
    }
   
    public List <T> getAND(String campos) {
        return this.getSeveral (MyRepository.AND, null,  campos);
    }
    public List <T> getAND(String args, String campos) {
        return this.getSeveral (MyRepository.AND, args,  campos);
    }
   
    public List <T> get(String campos) {
        return this.getSeveral(null, null, campos);
    }
    
    public List <T> get(String args, String campos) {
        return this.getSeveral(null, args, campos);
    }
        
    protected abstract List<T> getSeveral(String tipo_busqueda, String args, String campos);
}