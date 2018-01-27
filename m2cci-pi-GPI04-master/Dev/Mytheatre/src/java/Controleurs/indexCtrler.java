/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.DateFinaleDAO;
import DAO.FiltreSpectacleDateDAO;
import Modeles.SpectacleDatePhoto;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
 * @author lagierg
 */
@WebServlet(name = "indexCtrler", urlPatterns = {"/index"})
public class indexCtrler extends HttpServlet {

    public static final String vue = "/WEB-INF/index.jsp";

    @Resource(name = "jdbc/UFRIMA")
    private DataSource ds;

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                    new Locale("en"));
            Date currentDate = new Date();
            String dateMin = dateFormat.format(currentDate);
            Date dateM = DateFinaleDAO.DateFinale(ds);
            String dateMax = dateFormat.format(dateM);
            
            
            //request.getSession().setAttribute("user", "nonConnecte");
            request.getSession().setAttribute("dateMin", dateMin);
            request.getSession().setAttribute("dateMax", dateMax);

            request.getSession().setAttribute("dateMinFiltrage", dateMin);
            request.getSession().setAttribute("dateMaxFiltrage", dateMax);

            // on recupere ici toutes les photos d'affiche de tous les spectacles.
            List<SpectacleDatePhoto> lesSpectaclesFiltreDatePhoto = FiltrageDatePhoto(dateMin, dateMax, ds);

            request.setAttribute("SpectaclesPourAffiche", lesSpectaclesFiltreDatePhoto);
            request.getSession().setAttribute("listeSpectacles", lesSpectaclesFiltreDatePhoto);

            this.getServletContext().getRequestDispatcher(vue).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(indexCtrler.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex.getMessage(), ex);
        }
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

    public List<SpectacleDatePhoto> FiltrageDatePhoto(String date1, String date2, DataSource ds) throws SQLException { // date 1 sous forme yyyy-MM-dd
        List<SpectacleDatePhoto> lesSpectaclesFiltreDatePhoto;
        LocalDateTime ldtMin = LocalDateTime.parse(date1 + "T00:00");
        LocalDateTime ldtMax = LocalDateTime.parse(date2 + "T23:59:59");
        java.sql.Date dateMinFiltreSQL = new java.sql.Date(java.sql.Date.from(ldtMin.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        java.sql.Date dateMaxFiltreSQL = new java.sql.Date(java.sql.Date.from(ldtMax.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        lesSpectaclesFiltreDatePhoto = FiltreSpectacleDateDAO.FiltreSpectacleDatePhoto(ds, dateMinFiltreSQL, dateMaxFiltreSQL);
        return lesSpectaclesFiltreDatePhoto;

    }

}
