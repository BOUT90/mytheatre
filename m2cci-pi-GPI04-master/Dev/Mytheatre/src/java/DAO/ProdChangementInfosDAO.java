/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author niangd
 */
public class ProdChangementInfosDAO {

    public static boolean ProdChangementInfos(DataSource ds, String login, String nom, String prenom, String adresseemail,
            String numtelephone, String rue, String codepostal, String ville) throws SQLException {

        boolean modification = false;

        try (Connection conn = ds.getConnection()) {

            String MODIF_INFOS = "UPDATE  LESUTILISATEURS SET NOM=?, PRENOM=?, EMAIL=?, NUM_TELEPHONE=?, RUE=?, CODE_POSTAL=?, VILLE=? WHERE LOGIN =?";

            PreparedStatement pstmt = conn.prepareStatement(MODIF_INFOS);

            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, adresseemail);
            pstmt.setString(4, numtelephone);
            pstmt.setString(5, rue);
            pstmt.setString(6, codepostal);
            pstmt.setString(7, ville);
            pstmt.setString(8, login);

            if (pstmt.executeUpdate() != 0) {
                modification = true;
            }
        }
        return modification;
    }

}
