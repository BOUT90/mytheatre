/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;

import java.util.Date;

/**
 * Cette classe est utilsation pour la gestion des tickets
 *
 * @author niangd
 */
public class TicketRecap {

    private int noSerie;
    private int noPlace;
    private int noRang;
    private Date dateEmission;
    private String nomC;
    private int prix;

    public TicketRecap(int noSerie, int noPlace, int noRang, int prix, String nomC, Date dateEmission) {

        this.noSerie = noSerie;
        this.noPlace = noPlace;
        this.noRang = noRang;
        this.prix = prix;
        this.dateEmission = dateEmission;
        this.nomC = nomC;
    }

    public int getNoPlace() {
        return noPlace;
    }

    public int getPrix() {
        return prix;
    }
    

    /**
     * Retourne le numero de serie du ticket
     *
     * @return the noSerie
     */
    public int getNoSerie() {
        return noSerie;
    }


    /**
     * Retourne le numero de rang pour le ticket
     *
     * @return the noRang
     */
    public int getNoRang() {
        return noRang;
    }

    /**
     * Retourne la date d'émission du ticket
     *
     * @return the dateEmission
     */
    public Date getDateEmission() {
        return dateEmission;
    }

    /**
     * @return the nomC
     */
    public String getNomC() {
        return nomC;
    }

}
