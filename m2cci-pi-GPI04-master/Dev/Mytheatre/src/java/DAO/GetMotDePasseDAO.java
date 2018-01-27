package DAO;


import com.google.zxing.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author niangd
 */
public class GetMotDePasseDAO {

    public static String GetMotDePasse(DataSource ds, String login) throws SQLException {

        String password = "";

        String GET_PASSWORD = "SELECT PASSWORD FROM LESUTILISATEURS WHERE LOGIN=?";

        try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_PASSWORD)) {

            pstmt.setString(1, login);
         
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                password = rs.getString("PASSWORD");
            }

        }
        return password;

    }
}
