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
public class RecuperationStatusDAO {

    public static int RecuperationStatus(DataSource ds, String login) throws SQLException {

        int status = 0;

        String STATUS = "SELECT STATUS FROM LESUTILISATEURS WHERE LOGIN =?";

        try (Connection conn = ds.getConnection(); PreparedStatement psmt = conn.prepareStatement(STATUS)) {

            psmt.setString(1, login);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {

                status = rs.getInt("STATUS");
            }

        }
        return status;

    }

}
