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
public class VerficationConnectionDAO {

    public static int VerficationConnection(DataSource ds, String login, String motpass) throws SQLException {

        int status = -1;
        String PASS_WORD_lOGIN = "SELECT LOGIN, PASSWORD, STATUS FROM LESUTILISATEURS WHERE LOGIN=? AND PASSWORD=?";

        try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(PASS_WORD_lOGIN)) {

            stmt.setString(1, login);
            stmt.setString(2, motpass);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                status = rs.getInt("STATUS"); 

            }

        }
        return status;
    }
}
