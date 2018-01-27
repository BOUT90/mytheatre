/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author yassinea
 */
public class ClasseHelp {
    
    static final String FORMAT_DATE = "%02d-%02d-%d %02d:%02d";

    public static String ListeDatesToString(List<LocalDateTime> listeDate) {
        String str = "";
        for (LocalDateTime date : listeDate) {

            String dateStr = String.format(FORMAT_DATE,date.getDayOfMonth(),date.getMonthValue(),date.getYear(),date.getHour(),date.getMinute());

            str += dateStr + "\n";
            
        }
        return str;
    }
    
        public static String dateToString(LocalDateTime date) {
            String dateStr = String.format(FORMAT_DATE,date.getDayOfMonth(),date.getMonthValue(),date.getYear(),date.getHour(),date.getMinute());
        return dateStr;
    }
    
       

}
