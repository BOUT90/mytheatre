/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modeles.UtilisateurInfos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author niang
 */
public class InfosUtilisateurDAO {

    public static UtilisateurInfos utilisateur = null;

    public static UtilisateurInfos InfosUtilisateur(DataSource ds, String paramlogin) throws SQLException {

        String INFOS_UTILISATEUR = "SELECT LOGIN, NOM, PRENOM, EMAIL, NUM_TELEPHONE, RUE, VILLE, PASSWORD, CODE_POSTAL FROM LESUTILISATEURS WHERE LOGIN=?";

        try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(INFOS_UTILISATEUR)) {

            pstm.setString(1, paramlogin);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String login = rs.getString("LOGIN");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String email = rs.getString("EMAIL");
                String tel = rs.getString("NUM_TELEPHONE");
                String rue = rs.getString("RUE");
                String ville = rs.getString("VILLE");  
                String password = rs.getString("PASSWORD");  
                String codepostal = rs.getString("CODE_POSTAL");

                utilisateur = new UtilisateurInfos(login, nom, prenom, email, tel, rue, ville, password,codepostal);
            }

        }
        return utilisateur;

    }

}
