/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.NbPlacesDisponible;
import Modeles.SpectacleDate;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
@WebServlet(name = "ServletDetailSpectacle", urlPatterns = {"/Detail"})
public class ServletDetailSpectacle extends HttpServlet {

    @Resource(name = "jdbc/UFRIMA")
    private DataSource ds;
    public static final String vue = "/WEB-INF/detailSpectacle.jsp";

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

        // récuperer la liste de spéctacles sauvagrdée dans la session
        List<SpectacleDate> listeSpectacle = (List<SpectacleDate>) request.getSession().getAttribute("listeSpectacles");
        String nomSpectacleStr = (String) request.getParameter("nospectacle");
        request.getSession().setAttribute("nomSpectacleStr", nomSpectacleStr);
        int numSpectacle = Integer.parseInt(nomSpectacleStr);
        SpectacleDate spec = findSpectacle(listeSpectacle, numSpectacle);

        request.setAttribute("spec", spec);
        request.getSession().setAttribute("spectacle", spec);
        // Récupertion les places disponibles pour chaque présentation 
        List<Integer> listePlacesDispo = new ArrayList<>();
        for (int i = 0; i < spec.getListeDate().size(); i++) {

            try {
                int nbPlaces = NbPlacesDisponible.getNbPlacesDisponible(ds, spec.getNumeroSpectacle(), spec.getListeDate().get(i));
                listePlacesDispo.add(nbPlaces);
            } catch (SQLException ex) {
                Logger.getLogger(ServletDetailSpectacle.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ServletDetailSpectacle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getSession().setAttribute("listePlaces", listePlacesDispo);
        // fin de la récupération 
        this.getServletContext().getRequestDispatcher(vue).forward(request, response);

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
    // méthode permettant de chercher un spectables par numero

    private SpectacleDate findSpectacle(List<SpectacleDate> listeSpectacles, int num) {
        SpectacleDate spectacle = null;
        for (SpectacleDate spec : listeSpectacles) {
            if (spec.getNumeroSpectacle() == num) {
                spectacle = spec;
            }
        }
        return spectacle;
    }

}
