/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Cree un spectacle
 *
 * @author niangd
 */
public class SpectacleDate {

    private int numero;
    private String nom;
    private int duree;
    private String resume;
    private List<LocalDateTime> listeDate;

    public SpectacleDate(int numeroSpectacle, String nomSpectacle, int duree, String resumeSpectacle, List<LocalDateTime> d) {

        this.numero = numeroSpectacle;
        this.nom = nomSpectacle;
        this.duree = duree;
        this.resume = resumeSpectacle;
        this.listeDate = d;

    }

    /**
     * Retourne le numero de spectacle
     *
     * @return the numeroSpectacle
     */
    public int getNumeroSpectacle() {
        return numero;
    }

    /**
     * Retourne le nom de sepectacle
     *
     * @return the nomSpectacle
     */
    public String getNomSpectacle() {
        return nom;
    }

    /**
     * Retourne la duree du sepectacle
     *
     * @return the duree
     */
    public int getDuree() {
        return duree;
    }
//To change body of generated methods, choose Tools | Templates.

    /**
     * Le resume du spectacle
     *
     * @return the resumeSpectacle
     */
    public String getResumeSpectacle() {
        return resume;
    }

    /**
     * @return the date
     */
    public List<LocalDateTime> getListeDate() {
        return listeDate;
    }

//Affichage du numero, du nom, de la duree et du resumé du spectacle
    @Override
    public String toString() {
        return "-----Bienvenu dans Mytheatre : Spectacle --------\n"
                + "Le numéro de Spectacle : " + getNomSpectacle() + "\n"
                + "Film joué : " + getNomSpectacle() + "\n"
                + "Resume du film" + getResumeSpectacle() + "\n"
                + "La duree du film" + getDuree() + " \n";
    }

}
