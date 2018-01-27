/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.InfosUtilisateurDAO.utilisateur;
import Modeles.UtilisateurInfos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.sql.DataSource;

/**
 *
 * @author yassinea
 */
public class NbPlacesDisponible {

    public static int getNbPlacesDisponible(DataSource ds, int numSpec, LocalDateTime date) throws SQLException, ParseException {

        int nbPlaces = 0;
        String places_Dispo = "SELECT COUNT(*)AS NbPlaces FROM  LESTICKETS WHERE noSpec = ? and DATEREP = ?";

        try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(places_Dispo)) {

      
            java.sql.Date dateSQL = new java.sql.Date(java.sql.Date.from(date.atZone(ZoneId.systemDefault()).toInstant()).getTime());

            pstm.setInt(1, numSpec);
            pstm.setDate(2, dateSQL);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                nbPlaces = rs.getInt("NbPlaces");

            }

        }
        return nbPlaces;

    }
}
