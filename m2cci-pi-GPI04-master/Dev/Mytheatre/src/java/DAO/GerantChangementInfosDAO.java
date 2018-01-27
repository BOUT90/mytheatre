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
public class GerantChangementInfosDAO {
    
        public static boolean GerantChangementInfos(DataSource ds, String ancienlogin, String nouvlogin, String nom, String prenom, String adresseemail) throws SQLException {

        boolean modification = false;


        try (Connection conn = ds.getConnection()) {

            String MODIF_INFOS = "UPDATE  LESUTILISATEURS SET LOGIN=?, NOM=?, PRENOM=?, EMAIL=? WHERE LOGIN =?";

            PreparedStatement pstmt2 = conn.prepareStatement(MODIF_INFOS);
            
            pstmt2.setString(1, nouvlogin);
            pstmt2.setString(2, nom);
            pstmt2.setString(3, prenom);
            pstmt2.setString(4, adresseemail);
            pstmt2.setString(5, ancienlogin);

            if (pstmt2.executeUpdate() != 0) {
                modification = true;
            }
        }
        return modification;
    }
    
}
