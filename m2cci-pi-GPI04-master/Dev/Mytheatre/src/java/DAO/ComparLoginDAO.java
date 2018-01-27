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
public class ComparLoginDAO {

    public static boolean ComparLogin(DataSource ds, String login) throws SQLException {

        String LOGIN = "SELECT COUNT(LOGIN) FROM LESUTILISATEURS WHERE LOGIN =?";

        try (Connection conn = ds.getConnection(); PreparedStatement psmt = conn.prepareStatement(LOGIN)) {

            psmt.setString(1, login);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {

                return (rs.getInt(1) == 1);
            }

        }
        return false;

    }

}
