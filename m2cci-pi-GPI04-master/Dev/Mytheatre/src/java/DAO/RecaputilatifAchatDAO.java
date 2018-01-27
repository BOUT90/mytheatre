/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modeles.TicketRecap;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author yassinea
 */
public class RecaputilatifAchatDAO {

    public static List<TicketRecap> recaputilatifAchat(DataSource ds, int MaxnumDossier) throws SQLException {
        List<TicketRecap> Lestickets = new ArrayList<>();

        String LISTE_TICKETS_ZONE = "SELECT NOSERIE, NOPLACE, NORANG, DATEEMISSION, NOMC, PRIX FROM LESTICKETS NATURAL JOIN LESSIEGES NATURAL JOIN LESZONES NATURAL JOIN LESCATEGORIES WHERE NODOSSIER=?";

        try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(LISTE_TICKETS_ZONE)) {
            pstmt.setInt(1, MaxnumDossier);
            // exécution de la requête
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int numeroSerie = rs.getInt("NOSERIE");
                    int numeroPlace = rs.getInt("NOPLACE");
                    int numeroRang = rs.getInt("NORANG");
                    String nomCategorie = rs.getString("NOMC");
                    int prixZone = rs.getInt("PRIX");
                    Date dateemission =rs.getDate("DATEEMISSION");
                    Lestickets.add(new TicketRecap(numeroSerie, numeroPlace, numeroRang, prixZone, nomCategorie, dateemission));
                }

            }

        }
        return Lestickets;
    }
}
