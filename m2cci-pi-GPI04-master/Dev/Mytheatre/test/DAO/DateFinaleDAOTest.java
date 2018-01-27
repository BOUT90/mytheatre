/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.util.TestDataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author lagierg
 */
public class DateFinaleDAOTest {

    static DataSource ds = null;

    public DateFinaleDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException, SQLException {
        ds = new TestDataSource();
    }

    /**
     * Test of DateFinale method, of class DateFinaleDAO.
     */
    @Test
    public void testDateFinale() throws Exception {
        System.out.println("DateFinale");

        
        final String FORMAT = "yyyy-MM-dd";

        String date = "2017-03-24";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        Date expResult  = sdf.parse(date);

        Date result = DateFinaleDAO.DateFinale(ds);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

}
