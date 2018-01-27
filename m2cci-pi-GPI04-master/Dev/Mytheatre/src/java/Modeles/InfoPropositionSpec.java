/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;

import java.util.List;

/**
 *
 * @author yassinea
 */
public class InfoPropositionSpec {

    // info spectacle   
    private String nomAssociation;
    private String PrixUnitaireRepresentation;
    private String urlSpectacle;
    private String titreSpec;
    private String duree;
    private String resume;
    private String infoSupp;
    private List<String> listeDates;
    private List<String> listeheures;

    public InfoPropositionSpec(String nomAssociation, String PrixUnitaireRepresentation,
            String urlSpectacle, String titreSpec, String duree, String resume, String infoSupp, List<String> listeDates, List<String> listeheures) {
        this.nomAssociation = nomAssociation;
        this.PrixUnitaireRepresentation = PrixUnitaireRepresentation;
        this.urlSpectacle = urlSpectacle;
        this.titreSpec = titreSpec;
        this.duree = duree;
        this.resume = resume;
        this.infoSupp = infoSupp;
        this.listeDates = listeDates;
        this.listeheures = listeheures;
    }


    public String getNomAssociation() {
        return nomAssociation;
    }

    public void setNomAssociation(String nomAssociation) {
        this.nomAssociation = nomAssociation;
    }

    public String getPrixUnitaireRepresentation() {
        return PrixUnitaireRepresentation;
    }

    public void setPrixUnitaireRepresentation(String PrixUnitaireRepresentation) {
        this.PrixUnitaireRepresentation = PrixUnitaireRepresentation;
    }

    public String getUrlSpectacle() {
        return urlSpectacle;
    }

    public void setUrlSpectacle(String urlSpectacle) {
        this.urlSpectacle = urlSpectacle;
    }

    public String getTitreSpec() {
        return titreSpec;
    }

    public void setTitreSpec(String titreSpec) {
        this.titreSpec = titreSpec;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String prixUnitaire) {
        this.duree = prixUnitaire;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getInfoSupp() {
        return infoSupp;
    }

    public void setInfoSupp(String infoSupp) {
        this.infoSupp = infoSupp;
    }

    public List<String> getListeDates() {
        return listeDates;
    }

    public void setListeDates(List<String> listeDates) {
        this.listeDates = listeDates;
    }

    public List<String> getListeheures() {
        return listeheures;
    }

    public void setListeheures(List<String> listeheures) {
        this.listeheures = listeheures;
    }

}
