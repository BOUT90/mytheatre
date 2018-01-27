/*
 * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package DAO;

import java.io.StringWriter;
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
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.sql.DataSource;

/**
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class PlaceDAO {

    private static final String PLACES_VENDUES = "SELECT NOPLACE, NORANG, NOZ FROM LESTICKETS NATURAL JOIN LESSIEGES WHERE noSpec = ? and DATEREP = ?";

    private static final String ACHETER_PLACE = "INSERT INTO LESTICKETS (noSerie, noSpec, DateRep, NoPlace, NoRang, DateEmission, NoDossier) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String CREER_DOSSIER = "INSERT INTO LESDOSSIERS (NODOSSIER) VALUES (?)";

//    public static List<Place> placesVendues(DataSource ds, int spectacleId) throws SQLException {
//        try (Connection conn = ds.getConnection();
//                PreparedStatement pstmt = conn.prepareStatement(PLACES_VENDUES)) {
//            pstmt.setInt(1, spectacleId);
//            ResultSet rs = pstmt.executeQuery();
//            List<Place> places = new ArrayList<>();
//            while (rs.next()) {
//                places.add(new Place(rs.getInt("idplace"), rs.getInt("rang"), rs.getInt("colonne"), rs.getString("categorie").charAt(0)));
//            }
//            return places;
//        }
//    }
    public static String placesVenduesAsJSON(DataSource ds, int spectacleId, String date) throws SQLException, ParseException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(PLACES_VENDUES)) {

            pstmt.setInt(1, spectacleId);

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date dateJava = df.parse(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String dateReSrting = dateFormat.format(dateJava);
            LocalDateTime dateLDT = LocalDateTime.parse(dateReSrting);
            java.sql.Date dateSQL = new java.sql.Date(java.sql.Date.from(dateLDT.atZone(ZoneId.systemDefault()).toInstant()).getTime());

            pstmt.setDate(2, dateSQL);

            ResultSet rs = pstmt.executeQuery();

            StringWriter sw = new StringWriter();
            JsonGenerator gen = Json.createGenerator(sw);
            gen.writeStartObject()
                    .writeStartArray("bookings");

            while (rs.next()) {
                int numPlace = rs.getInt("noPlace");
                int colonne;

                if (numPlace <= 5) {
                    colonne = numPlace;
                } else if (numPlace > 5 && numPlace <= 15) {
                    colonne = numPlace + 2;
                } else {
                    colonne = numPlace + 4;
                }

                gen.writeStartObject()
                        .write("placeId", (numPlace + rs.getInt("noRang") * 20 - 20))
                        .write("rang", rs.getInt("noRang"))
                        .write("colonne", colonne)
                        .writeEnd();
            }
            gen.writeEnd()
                    .writeEnd();
            gen.close();
            return sw.toString();
        }
    }

    /**
     *
     * @param ds
     * @param numSpectacle
     * @param maxs
     * @param placesIds
     * @param date
     * @throws SQLException
     */
    public static void acheterPlace(DataSource ds, int numSpectacle, int[] placesIds, int[] maxs, String date) throws SQLException, ParseException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt1 = conn.prepareStatement(CREER_DOSSIER);
                    PreparedStatement pstmt2 = conn.prepareStatement(ACHETER_PLACE)) {
                conn.setAutoCommit(false);
                int maxDossier = maxs[1] + 1;
                pstmt1.setInt(1, maxDossier);
                pstmt1.addBatch();
                pstmt1.executeBatch();

                int maxNumSerie = maxs[0] + 1;
                for (int idPlace : placesIds) {
                    pstmt2.setInt(1, maxNumSerie++);
                    pstmt2.setInt(2, numSpectacle);

                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    //String dateS = date;
                    Date dateJava = df.parse(date);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String dateReSrting = dateFormat.format(dateJava);
                    LocalDateTime dateLDT = LocalDateTime.parse(dateReSrting);
                    java.sql.Date dateSQL = new java.sql.Date(java.sql.Date.from(dateLDT.atZone(ZoneId.systemDefault()).toInstant()).getTime());

                    pstmt2.setDate(3, dateSQL);
                    int numRang = (idPlace - 1 + 20) / 20;
                    int numPlace = (idPlace + 20) - numRang * 20;
                    pstmt2.setInt(4, numPlace);
                    pstmt2.setInt(5, numRang);

                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date currentDate = new Date();
                    String currentDateString = dateFormat2.format(currentDate);
                    LocalDateTime LDTDate = LocalDateTime.parse(currentDateString + "T00:00");
                    java.sql.Date dateSQL2 = new java.sql.Date(java.sql.Date.from(LDTDate.atZone(ZoneId.systemDefault()).toInstant()).getTime());

                    pstmt2.setDate(6, dateSQL2);
                    pstmt2.setInt(7, maxs[1] + 1);

                    pstmt2.addBatch();
                }
                pstmt2.executeBatch();
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
