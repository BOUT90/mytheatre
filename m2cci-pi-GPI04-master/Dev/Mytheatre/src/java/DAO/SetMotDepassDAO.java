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
public class SetMotDepassDAO {

    public static void SetMotDepass (DataSource ds, String password, String login) throws SQLException {

        try (Connection conn = ds.getConnection()) {

            String MODIF_INFOS = "UPDATE  LESUTILISATEURS SET PASSWORD=? WHERE LOGIN =?";

            PreparedStatement pstmt = conn.prepareStatement(MODIF_INFOS);

            pstmt.setString(1, password);
            pstmt.setString(2, login);

            pstmt.executeUpdate();

        }

    }

}
