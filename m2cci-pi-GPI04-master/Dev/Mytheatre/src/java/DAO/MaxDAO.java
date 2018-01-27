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
import javax.sql.DataSource;

/**
 *
 * @author lagierg
 */
public class MaxDAO {

    private final static String MAX_NUM_SERIE_ET_NUM_DOSSIER = "SELECT MAX(NOSERIE) AS NOSERIE, MAX(NODOSSIER) AS NODOSSIER FROM LESTICKETS";
    private final static String MAX_NUM_SPECTACLE = "SELECT MAX(NOSPEC) AS NOSPEC FROM LESSPECTACLES";
    private final static String MAX_NUM_PHOTO = "SELECT MAX(NOPHOTO) AS NOPHOTO FROM LESPHOTOS";

    protected static int[] maxs = new int[2];

    public static int[] MaxNumSerie_NumDossier(DataSource ds) throws SQLException {

        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {

            maxs[0] = 0;
            maxs[1] = 0;

            ResultSet rs = stmt.executeQuery(MAX_NUM_SERIE_ET_NUM_DOSSIER);
            while (rs.next()) {
                maxs[0] = rs.getInt("NOSERIE");
                maxs[1] = rs.getInt("NODOSSIER");

            }

        }
        return maxs;
    }

    public static int getNumSpectacleMax(DataSource ds) throws SQLException {

        int numSpectacleMax = 0;

        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(MAX_NUM_SPECTACLE);
            while(rs.next()){
                numSpectacleMax = rs.getInt("NUMSPEC");
            }
        }

        return numSpectacleMax;

    }
    
    public static int getNumPhotoMax(DataSource ds) throws SQLException {

        int numSpectacleMax = 0;

        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(MAX_NUM_PHOTO);
            while(rs.next()){
            numSpectacleMax = rs.getInt("NUMPHOTO");
            }
        }

        return numSpectacleMax;

    }
}
