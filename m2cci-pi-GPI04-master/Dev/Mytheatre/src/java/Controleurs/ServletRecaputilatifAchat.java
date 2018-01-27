/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.MaxDAO;
import DAO.RecaputilatifAchatDAO;
import Modeles.SpectacleDate;
import Modeles.TicketRecap;
import Modeles.InfoPropositionSpec;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author yassinea
 */
@WebServlet(name = "ServletRecaputilatifAchat", urlPatterns = {"/ServletRecaputilatifAchat"})
public class ServletRecaputilatifAchat extends HttpServlet {

    @Resource(name = "jdbc/UFRIMA")
    DataSource ds;

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
        try {
            int[] maxs = MaxDAO.MaxNumSerie_NumDossier(ds);
            List<TicketRecap> liste = RecaputilatifAchatDAO.recaputilatifAchat(ds, maxs[1]);
            request.getSession().setAttribute("listeTickets", liste);
            request.getSession().setAttribute("noDossier", maxs[1]);
            int PrixTotal = prixTotal(liste);
            request.getSession().setAttribute("prixTotal", PrixTotal);

            // test
//            List<String> dateTest = new ArrayList<String>();
//
//            dateTest.add("10/01/2017");
//            dateTest.add("11/01/2017");
//            dateTest.add("12/01/2017");
//
//            List<String> heureTest = new ArrayList<String>();
//
//            heureTest.add("10:45");
//            heureTest.add("11:50");
//            heureTest.add("12:10");
//
//            //SpectacleDate specDate=new SpectacleDate(1, "abed", 120,"resumé",dateTest);
//            InfoPropositionSpec user = new InfoPropositionSpec("nom",  "prenom",  "email",  "adresse",  "nomAsso ciati onjsd gsbshf gnjsdc cgysdc cgcgc gcxydscgf",  "PrixUnitaireRepresentation", 
//             "numTel",  "urlSpectacle",  "titreSpec",  "duree",  "resume resumeresume resumeresume resumeresume resumeresume resumeresume resumeresume resumeresume resume ",  "infoSupp",dateTest,heureTest);
//            request.getSession().setAttribute("userTest", user);

            //fin test
            request.getRequestDispatcher("/WEB-INF/achat.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletRecaputilatifAchat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int prixTotal(List<TicketRecap> liste) {
        int prixTotal = 0;
        for (TicketRecap unTicket : liste) {
            prixTotal += unTicket.getPrix();
        }
        return prixTotal;

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
