/*
 * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
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
package Modeles;

/**
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class Place {

    /**
     * le numéro de la place
     */
    private final int noPlace;

    /**
     * le rang de la place
     */
    private final int rang;

    /**
     * la position de la place dans le rang
     */
    private final int colonne;

    /**
     * la categorie de la place
     */
    private final char categorie;

    /**
     *
     * @param numPlace
     * @param numRang le rang
     * @param numZone la catégorie
     */
    public Place(int numPlace, int numRang, int numZone) {

        this.noPlace = numPlace + numRang * 20 - 20;
        this.rang = numRang;
        
        if (numPlace <= 5){
        this.colonne = numPlace;
                } else if (numPlace > 5 && numPlace <= 15){
                    this.colonne = numPlace + 2;
                } else {
                    this.colonne = numPlace + 4;
                }
        
        switch (numZone) {
            case 1:
                this.categorie = 'A';
                break;
            case 2:
                this.categorie = 'B';
                break;
            case 3:
                this.categorie = 'C';
                break;
            case 4:
                this.categorie = 'D';
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int getNoPlace() {
        return noPlace;
    }

    public int getRang() {
        return rang;
    }

    public int getColonne() {
        return colonne;
    }

    public char getCategorie() {
        return categorie;
    }

}
