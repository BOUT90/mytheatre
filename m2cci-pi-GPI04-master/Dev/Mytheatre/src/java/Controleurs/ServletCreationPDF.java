/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import Modeles.SpectacleDate;
import Modeles.TicketRecap;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yassinea
 */
@WebServlet(name = "ServletCreationPDF", urlPatterns = {"/ServletCreationPDF"})
public class ServletCreationPDF extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<TicketRecap> listeTickets = (List<TicketRecap>) request.getSession().getAttribute("listeTickets");

        SpectacleDate spec = (SpectacleDate) request.getSession().getAttribute("spectacle");

        String date = (String) request.getSession().getAttribute("date");

        int noDossier = (Integer) request.getSession().getAttribute("noDossier");

        int prixTotal = (Integer) request.getSession().getAttribute("prixTotal");

        float remise = (float) request.getSession().getAttribute("remise");

        // Créer une police
        Font red = FontFactory.getFont(FontFactory.HELVETICA, 24,
                Font.BOLD, new BaseColor(0x00, 0x00, 0xFF));

        Document document = new Document() {
        };
        try {
            /* setup http header */
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", " inline; filename=report.pdf");

            /* generate PDF document */
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.addTitle("Recap");

            Paragraph p1 = new Paragraph("");
            p1.setFirstLineIndent(120);
            p1.setSpacingAfter(30);
            p1.add(new Chunk("Récapitulatif de votre achat", red));
            document.add(p1);

//            Paragraph p2 = new Paragraph("    ", new Font(Font.HELVETICA, 24));
//            p2.setSpacingAfter(1);
//            document.add(p2);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
            Date dateCurrent = new Date();
            String dateStr = dateFormat.format(dateCurrent);

            Paragraph p3 = new Paragraph("Numero Dossier: " + noDossier, new Font(Font.HELVETICA, 12));
            p3.setSpacingAfter(8);
            document.add(p3);

            Paragraph p4 = new Paragraph("Date d'achat: " + dateStr, new Font(Font.HELVETICA, 12));
            p4.setSpacingAfter(8);
            document.add(p4);

            Paragraph p5 = new Paragraph("Spectacle : " + spec.getNomSpectacle() + ": " + date, new Font(Font.HELVETICA, 12));
            p5.setSpacingAfter(15);
            document.add(p5);

            String ticketStr;
            if (listeTickets.size() == 1) {
                ticketStr = "Ticket : ";
            } else {
                ticketStr = "Tickets : ";
            }
            Paragraph p7 = new Paragraph(ticketStr, new Font(Font.HELVETICA, 18));
            p7.setSpacingAfter(10);
            document.add(p7);

            /* New table - 3 columns of *relative* width 2/4/1 */
            PdfPTable table = new PdfPTable(4);

            /* Add the three headings */
            Font headingStyle = new Font(Font.HELVETICA, Font.DEFAULTSIZE, Font.BOLD);

            table.addCell(new Phrase("Numéro de Place", headingStyle));
            table.addCell(new Phrase("Rang", headingStyle));
            table.addCell(new Phrase("Zone", headingStyle));
            table.addCell(new Phrase("Prix", headingStyle));
            table.setHorizontalAlignment(Element.ALIGN_LEFT);

            for (TicketRecap ticket : listeTickets) {
                table.addCell(String.valueOf(ticket.getNoPlace()));
                table.addCell(String.valueOf(ticket.getNoRang()));
                table.addCell(ticket.getNomC());
                table.addCell(String.valueOf(ticket.getPrix()) + "€");
            }
            document.add(table);

            Paragraph p9 = new Paragraph("    ", new Font(Font.HELVETICA, 10));
            p9.setSpacingAfter(4);
            document.add(p9);

            if (listeTickets.size() < 10) {
                Paragraph p10 = new Paragraph("Prix Total: " + prixTotal + "€", new Font(Font.HELVETICA, 12));
                p10.setSpacingAfter(8);
                document.add(p10);
            } else {
                Paragraph p11 = new Paragraph("Remise: " + remise + "€", new Font(Font.HELVETICA, 12));
                p11.setSpacingAfter(8);
                document.add(p11);

                float prixtotalApresRemise = prixTotal - remise;
                Paragraph p12 = new Paragraph("Prix total avec remise " + prixtotalApresRemise + "€", new Font(Font.HELVETICA, 12));
                p12.setSpacingAfter(8);
                document.add(p12);
            }

        } catch (DocumentException de) {
            // Wrap inside a ServletException
            throw new ServletException(de);
        }

        document.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
