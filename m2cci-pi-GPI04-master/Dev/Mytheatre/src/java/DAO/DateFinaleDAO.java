/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.sql.DataSource;

/**
 *
 * @author lagierg
 */
public class DateFinaleDAO {

    public static Date DateFinale(DataSource ds) throws SQLException {

        Date dateFinale;

        try (Connection conn = ds.getConnection()) {

            String DATE_FINALE = "SELECT MAX(DATEREP) AS DATEREPMAX FROM LESREPRESENTATIONS";

            try (Statement stmt = conn.createStatement()) {
                // exécution de la requête
                try (ResultSet rs = stmt.executeQuery(DATE_FINALE)) {
                    rs.next();
                    dateFinale = rs.getDate("DATEREPMAX");

                }
                return dateFinale;
            }

        }

    }
}
