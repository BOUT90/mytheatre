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
package Controleurs;

import DAO.MaxDAO;
import DAO.PlaceDAO;
import Modeles.SpectacleDate;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
@WebServlet(name = "AcheterPlacesCtrler", urlPatterns = {"/acheterPlaces"})
public class AcheterPlacesCtrler extends HttpServlet {

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
            String[] places = request.getParameterValues("place");
            String date = request.getParameter("date");
            request.getSession().setAttribute("date", date);
            
            int[] placesIds = new int[places.length];
            for (int i = 0; i < places.length; i++) {
                placesIds[i] = Integer.parseInt(places[i]);
            }
            HttpSession session = request.getSession();
            SpectacleDate spectacle = (SpectacleDate) session.getAttribute("spectacle");
            
            int[] maxs = MaxDAO.MaxNumSerie_NumDossier(ds);
            
            try {
                PlaceDAO.acheterPlace(ds,spectacle.getNumeroSpectacle(), placesIds, maxs, date);
            } catch (ParseException ex) {
                Logger.getLogger(AcheterPlacesCtrler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.getRequestDispatcher("/ServletRecaputilatifAchat").forward(request, response);
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
