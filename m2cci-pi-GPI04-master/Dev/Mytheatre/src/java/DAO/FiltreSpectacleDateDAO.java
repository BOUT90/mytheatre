/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.SpectacleDateDAO.contain;
import Modeles.SpectacleDate;
import Modeles.SpectacleDatePhoto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author niangd
 */
public class FiltreSpectacleDateDAO {

    private final static String LIST_SPECTACLE_SELON_DATE = "SELECT NOSPEC, NOMS, DUREE, RESUME, DATEREP FROM LESSPECTACLES NATURAL JOIN LESREPRESENTATIONS WHERE DATEREP >= ? AND DATEREP <= ? ORDER BY NOSPEC, DATEREP";
    private final static String LIST_PHOTO_SPECTACLE = "SELECT URL FROM LESPHOTOS NATURAL JOIN LESPHOTOSSPECTACLE WHERE NOSPEC = ?";

    public static List<SpectacleDate> FiltreSpectacleDate(DataSource ds, Date debut, Date fin) throws SQLException {

        List<SpectacleDate> LesFiltreSpectacle = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {

            try (PreparedStatement stmt = conn.prepareStatement(LIST_SPECTACLE_SELON_DATE)) {
                stmt.setDate(1, debut);
                stmt.setDate(2, fin);
                try (ResultSet rs = stmt.executeQuery()) {

                    int numeroSpectaclePrecedent = 0;
                    SpectacleDate spec = null;

                    while (rs.next()) {
                        int numeroSpectacle = rs.getInt("NOSPEC");
                        String nomSpectacle = rs.getString("NOMS");
                        int duree = rs.getInt("DUREE");
                        String resumeSpectacle = rs.getString("RESUME");
                        Date date = rs.getDate(5);
                        Time time = rs.getTime(5);
                        LocalDateTime ldt = LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
                        List<LocalDateTime> listeDate = new ArrayList<>();
                        listeDate.add(ldt);
                        if (numeroSpectacle != numeroSpectaclePrecedent) {
                            spec = new SpectacleDate(numeroSpectacle, nomSpectacle, duree, resumeSpectacle, listeDate);
                            numeroSpectaclePrecedent = numeroSpectacle;
                            LesFiltreSpectacle.add(spec);
                        } else {
                            spec.getListeDate().add(ldt);    // le premier spectacle doit avoir le numéro 1, et en ordre croissant sans vide.                       
                        }
                    }
                    return LesFiltreSpectacle;
                }

            }
        }
    }

    public static List<SpectacleDatePhoto> FiltreSpectacleDatePhoto(DataSource ds, Date debut, Date fin) throws SQLException {

        List<SpectacleDatePhoto> LesFiltreSpectaclePhoto = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {

            try (PreparedStatement stmt = conn.prepareStatement(LIST_SPECTACLE_SELON_DATE)) {
                stmt.setDate(1, debut);
                stmt.setDate(2, fin);
                try (ResultSet rs = stmt.executeQuery()) {

                    int numSpectaclePrecedent = 0;

                    while (rs.next()) {
                        int numeroSpectacle = rs.getInt("NOSPEC");
                        String nomSpectacle = rs.getString("NOMS");
                        int duree = rs.getInt("DUREE");
                        String resumeSpectacle = rs.getString("RESUME");
                        Date date = rs.getDate(5);
                        Time time = rs.getTime(5);
                        LocalDateTime ldt = LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
                        List<LocalDateTime> listeDate = new ArrayList<>();
                        listeDate.add(ldt);
                        List<String> photos = new ArrayList<>();

                        if (numeroSpectacle != numSpectaclePrecedent) { // pour n'accéder à la BD q'une fois par spectacle
                            try (PreparedStatement stmt2 = conn.prepareStatement(LIST_PHOTO_SPECTACLE)) {
                                stmt2.setInt(1, numeroSpectacle);
                                try (ResultSet rs2 = stmt2.executeQuery()) {
                                    while (rs2.next()) {
                                        String url = rs2.getString("URL");
                                        photos.add(url);
                                    }
                                }
                            }
                        }
                        numSpectaclePrecedent = numeroSpectacle;

                        SpectacleDatePhoto spec = new SpectacleDatePhoto(numeroSpectacle, nomSpectacle, duree, resumeSpectacle, listeDate, photos);
                        if (!contain(LesFiltreSpectaclePhoto, spec)) {

                            LesFiltreSpectaclePhoto.add(spec);
                        } else {
                            for (int i = 0; i < LesFiltreSpectaclePhoto.size(); i++) {
                                if (LesFiltreSpectaclePhoto.get(i).getNumeroSpectacle() == spec.getNumeroSpectacle()) {
                                    LesFiltreSpectaclePhoto.get(i).getListeDate().add(ldt);
                                    break;
                                }
                            }
                        }
                    }
                    return LesFiltreSpectaclePhoto;
                }

            }
        }
    }

}
