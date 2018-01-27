/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author niangd
 */
public class CreationCompteDAO {

    public static boolean CreationCompte(DataSource ds, String motdepasse, String pseudo, String nom, String prenom, String numtelephone,
            String rue,  String ville, String email, String codepostal) throws SQLException {

        boolean comptecree = false;
        String INSERTION_INFOS = "INSERT INTO LESUTILISATEURS (PASSWORD, LOGIN, NOM, PRENOM, STATUS, NUM_TELEPHONE, RUE, VILLE, EMAIL, CODE_POSTAL) VALUES(?, ?, ?,?,'0', ?, ?, ?,?,?)";

        try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(INSERTION_INFOS)) {

            pstmt.setString(1, motdepasse);
            pstmt.setString(2, pseudo);
            pstmt.setString(3, nom);
            pstmt.setString(4, prenom);
            pstmt.setString(5, numtelephone);
            pstmt.setString(6, rue);
            pstmt.setString(7, ville);
            pstmt.setString(8, email);
            pstmt.setString(9, codepostal);

            if (pstmt.executeUpdate() != 0) {
                comptecree = true;
            }

        }
        return comptecree;

    }

}
