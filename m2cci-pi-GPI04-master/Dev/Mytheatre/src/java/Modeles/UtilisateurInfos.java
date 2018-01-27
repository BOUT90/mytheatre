/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;

/**
 *
 * @author niang
 */
public class UtilisateurInfos {
    
    private String pseudo;
    private String nom;
    private String prenom;
    private String mail;
    private String tel;
    private String rue;
    private String ville;
    private String password;
    private String codepostal;
    

    public UtilisateurInfos(String pseudo, String nom, String prenom, String mail, String tel, String rue, String ville, String password, String codepostal) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.rue = rue;
        this.ville = ville;
        this.password = password;
        this.codepostal=codepostal;
        
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }


    public String getMail() {
        return mail;
    }

    public String getTel() {
        return tel;
    }

    public String getRue() {
        return rue;
    }

    public String getVille() {
        return ville;
    }

    /**
     * @return the codepostal
     */
    public String getCodepostal() {
        return codepostal;
    }
    
    
    
    
    
}
