/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modeles.SpectacleDate;
import Modeles.SpectacleDatePhoto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/* cette classe est utilisee pour l'acces à la base donnes pour la recuperation
 *des spectacles existants
 *
 * @author niangd
 */
public class SpectacleDateDAO {

    private static final String LISTE_SPECTACLE = "SELECT NOSPEC, NOMS, DUREE, RESUME, DATEREP FROM LESSPECTACLES NATURAL JOIN LESREPRESENTATIONS";

    private static final String AJOUTER_SPECTACLE = "INSERT INTO LESSPECTACLES (NOSPEC, NOMS, DUREE, RESUME) VALUES (?, ?, ?, ?)";

    private static final String AJOUTER_REPRESENTATION = "INSERT INTO LESREPRESENTATIONS (DATEREP, NUMSPEC) VALUES (?, ?)";

    private static final String AJOUTER_PHOTOS = "INSERT INTO LESPHOTOS (NOPHOTO, URL) VALUES (?, ?)";

    private static final String AJOUTER_PHOTOS_SPECTACLE = "INSERT INTO LESPHOTOSSPECTACLES (NOPHOTO, NOSPEC) VALUES (?, ?)";

    public static List<SpectacleDate> Spectacle(DataSource ds) throws SQLException {
        List<SpectacleDate> LesSpectacle = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {

            try (Statement stmt = conn.createStatement()) {
                // exécution de la requête
                try (ResultSet rs = stmt.executeQuery(LISTE_SPECTACLE)) {
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
                        SpectacleDate spec = new SpectacleDate(numeroSpectacle, nomSpectacle, duree, resumeSpectacle, listeDate);
                        if (!contain(LesSpectacle, spec)) {
                            LesSpectacle.add(spec);
                        } else {
                            for (int i = 0; i < LesSpectacle.size(); i++) {
                                if (LesSpectacle.get(i).getNumeroSpectacle() == spec.getNumeroSpectacle()) {
                                    LesSpectacle.get(i).getListeDate().add(ldt);
                                    break;
                                }
                            }
                        }

                    }
                    return LesSpectacle;
                }

            }
        }
    }

    public static boolean contain(List<SpectacleDate> listeSpec, SpectacleDate spec) {
        boolean estPresente = false;
        for (SpectacleDate spectacle : listeSpec) {
            if (spectacle.getNumeroSpectacle() == spec.getNumeroSpectacle()) {
                estPresente = true;
                break;
            }
        }
        return estPresente;
    }

    public static boolean contain(List<SpectacleDatePhoto> listeSpec, SpectacleDatePhoto spec) {
        boolean estPresente = false;
        for (SpectacleDate spectacle : listeSpec) {
            if (spectacle.getNumeroSpectacle() == spec.getNumeroSpectacle()) {
                estPresente = true;
                break;
            }
        }
        return estPresente;
    }

    // Ajout d'un spectacle et ses représentation dans la BD
    public static void ajouterSpectacleDate(DataSource ds, SpectacleDate spec) throws SQLException {

        try (Connection conn = ds.getConnection()) {

            try (PreparedStatement pstmt1 = conn.prepareStatement(AJOUTER_SPECTACLE)) {
                conn.setAutoCommit(false);
                pstmt1.setInt(1, spec.getNumeroSpectacle());
                pstmt1.setString(2, spec.getNomSpectacle());
                pstmt1.setInt(3, spec.getDuree());
                pstmt1.setString(4, spec.getResumeSpectacle());

                pstmt1.addBatch();
                pstmt1.executeBatch();

                for (LocalDateTime date : spec.getListeDate()) {
                    try (PreparedStatement pstmt2 = conn.prepareStatement(AJOUTER_REPRESENTATION)) {
                        java.sql.Date dateSQL = new java.sql.Date(java.sql.Date.from(date.atZone(ZoneId.systemDefault()).toInstant()).getTime());
                        pstmt2.setDate(1, dateSQL);
                        pstmt2.setInt(2, spec.getNumeroSpectacle());
                        pstmt2.addBatch();
                        pstmt2.executeBatch();
                    }
                }

                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }

    }

    // Ajout d'un spectacle, de ses représentations et de ses photos dans la BD
    public static void ajouterSpectacleDatePhoto(DataSource ds, SpectacleDatePhoto spec, int numPhotoMax) throws SQLException {

        try (Connection conn = ds.getConnection()) {

            try (PreparedStatement pstmt1 = conn.prepareStatement(AJOUTER_SPECTACLE)) {
                conn.setAutoCommit(false);
                pstmt1.setInt(1, spec.getNumeroSpectacle());
                pstmt1.setString(2, spec.getNomSpectacle());
                pstmt1.setInt(3, spec.getDuree());
                pstmt1.setString(4, spec.getResumeSpectacle());

                pstmt1.addBatch();
                pstmt1.executeBatch();

                for (LocalDateTime date : spec.getListeDate()) {
                    try (PreparedStatement pstmt2 = conn.prepareStatement(AJOUTER_REPRESENTATION)) {
                        java.sql.Date dateSQL = new java.sql.Date(java.sql.Date.from(date.atZone(ZoneId.systemDefault()).toInstant()).getTime());
                        pstmt2.setDate(1, dateSQL);
                        pstmt2.setInt(2, spec.getNumeroSpectacle());
                        pstmt2.addBatch();
                        pstmt2.executeBatch();
                    }
                }

                try (PreparedStatement pstmt3 = conn.prepareStatement(AJOUTER_PHOTOS)) {
                    int numPhoto = numPhotoMax + 1;
                    for (String photo : spec.getPhotos()) {
                        pstmt3.setInt(1, (numPhoto++));
                        pstmt3.setString(2, photo);
                        pstmt3.addBatch();
                        pstmt3.executeBatch();
                    }
                }

                try (PreparedStatement pstmt4 = conn.prepareStatement(AJOUTER_PHOTOS_SPECTACLE)) {
                    int numPhoto = numPhotoMax + 1;
                    for (String photo : spec.getPhotos()) {
                        pstmt4.setInt(1, (numPhoto++));
                        pstmt4.setInt(2, spec.getNumeroSpectacle());
                        pstmt4.addBatch();
                        pstmt4.executeBatch();
                    }
                }

                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }

    }
}
