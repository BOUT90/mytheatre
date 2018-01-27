/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.util.TestDataSource;
import Modeles.SpectacleDate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author yassinea
 */
public class SpectacleDAOTest {

    static DataSource ds = null;

    public SpectacleDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException, SQLException {
        ds = new TestDataSource();
    }
    /**
     * Test of Spectacle method, of class SpectacleDAO.
     */
    @Test
    public void testSpectacle() throws SQLException  {
        System.out.println("Spectacle");
        List<SpectacleDate> result = SpectacleDateDAO.Spectacle(ds);
        assertEquals(11, result.size());
    }

}
