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
public class EstadisicasBean {
    
    private int altes;
    private int altesEsterilitzat;
    private int altesXip;
    private int altesMascle;
    private int altesFemella;
    private int inactius;
    private int adoptats;
    private int adoptatsMascle;
    private int adoptatsFemella;

    public EstadisicasBean(int altes, int altesEsterilitzat, int altesXip, int altesMascle, int altesFemella, int inactius, int adoptats, int adoptatsMascle, int adoptatsFemella) {
        this.altes = altes;
        this.altesEsterilitzat = altesEsterilitzat;
        this.altesXip = altesXip;
        this.altesMascle = altesMascle;
        this.altesFemella = altesFemella;
        this.inactius = inactius;
        this.adoptats = adoptats;
        this.adoptatsMascle = adoptatsMascle;
        this.adoptatsFemella = adoptatsFemella;
    }

    public EstadisicasBean() {
    }
    
    

    public int getAltes() {
        return altes;
    }

    public void setAltes(int altes) {
        this.altes = altes;
    }

    public int getAltesEsterilitzat() {
        return altesEsterilitzat;
    }

    public void setAltesEsterilitzat(int altesEsterilitzat) {
        this.altesEsterilitzat = altesEsterilitzat;
    }

    public int getAltesXip() {
        return altesXip;
    }

    public void setAltesXip(int altesXip) {
        this.altesXip = altesXip;
    }

    public int getAltesMascle() {
        return altesMascle;
    }

    public void setAltesMascle(int altesMascle) {
        this.altesMascle = altesMascle;
    }

    public int getAltesFemella() {
        return altesFemella;
    }

    public void setAltesFemella(int altesFemella) {
        this.altesFemella = altesFemella;
    }

    public int getInactius() {
        return inactius;
    }

    public void setInactius(int inactius) {
        this.inactius = inactius;
    }

    public int getAdoptats() {
        return adoptats;
    }

    public void setAdoptats(int adoptats) {
        this.adoptats = adoptats;
    }

    public int getAdoptatsMascle() {
        return adoptatsMascle;
    }

    public void setAdoptatsMascle(int adoptatsMascle) {
        this.adoptatsMascle = adoptatsMascle;
    }

    public int getAdoptatsFemella() {
        return adoptatsFemella;
    }

    public void setAdoptatsFemella(int adoptatsFemella) {
        this.adoptatsFemella = adoptatsFemella;
    }
    
    
    
            
            
            
}
