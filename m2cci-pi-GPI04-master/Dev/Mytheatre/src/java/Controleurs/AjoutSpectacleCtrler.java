/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.MaxDAO;
import DAO.SpectacleDateDAO;
import Modeles.SpectacleDate;
import Modeles.SpectacleDatePhoto;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "AjoutSpectacle", urlPatterns = {"/AjoutSpectacle"})
public class AjoutSpectacleCtrler extends HttpServlet {

    @Resource(name = "jdbc/UFRIMA")
    private DataSource ds;

    public static final String vue = "/WEB-INF/ajoutSpectacle.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nom = request.getParameter("nom");
            String resume = request.getParameter("resume");
            int duree = Integer.parseInt(request.getParameter("duree"));
            int nbDateTot = Integer.parseInt(request.getParameter("nbDateTot"));
            int nbPhotoTot = Integer.parseInt(request.getParameter("nbPhotoTot"));

            List<LocalDateTime> dates = new ArrayList<>(); // La liste pour les dates de représentation.
            for (int i = 1; i <= nbDateTot; i++) {
                String date = request.getParameter("date" + i);
                String time = request.getParameter("time" + i);
                LocalDateTime ldtDate = LocalDateTime.parse(date + "T" + time);
                dates.add(ldtDate);
            }
            List<String> urlPhotos = new ArrayList<>(); // La liste pour les photos.
            for (int i = 1; i <= nbPhotoTot; i++) {
                String url = request.getParameter("urlPhoto" + i);
                if (!url.equals("")) {
                    urlPhotos.add(url);
                }
            }
            int numéroSpectacleMax = MaxDAO.getNumSpectacleMax(ds);
            SpectacleDatePhoto spectaclePhotoAAjoute = new SpectacleDatePhoto(numéroSpectacleMax + 1, nom, duree, resume, dates, urlPhotos);
            SpectacleDate spectacleAAjoute = new SpectacleDate(numéroSpectacleMax + 1, nom, duree, resume, dates);

            //SpectacleDateDAO.ajouterSpectacleDate(ds, spectacleAAjoute);
            int numPhotoMax = MaxDAO.getNumPhotoMax(ds);
            SpectacleDateDAO.ajouterSpectacleDatePhoto(ds, spectaclePhotoAAjoute, numPhotoMax);

            this.getServletContext().getRequestDispatcher(vue).forward(request, response);

        } catch (SQLException ex) {
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

}
