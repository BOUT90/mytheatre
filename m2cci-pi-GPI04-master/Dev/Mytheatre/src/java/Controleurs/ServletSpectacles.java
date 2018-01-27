/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.DateFinaleDAO;
import DAO.FiltreSpectacleDateDAO;
import Modeles.SpectacleDate;
import Modeles.SpectacleDatePhoto;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
 * cette servlet permet d'affichage tous les spectacles.
 *
 * @author yassinea
 */
@WebServlet(name = "servletSpectacle", urlPatterns = {"/Spectacle"})
public class ServletSpectacles extends HttpServlet {

    public static final String vue = "/WEB-INF/spectacles.jsp";

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
            Date d = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                    new Locale("en"));
            Date currentDate = new Date();
            String dateMin = dateFormat.format(currentDate);
            Date dateM = DateFinaleDAO.DateFinale(ds);
            String dateMax = dateFormat.format(dateM);

            // Liste contenant les spectacles venant de la base de donnée
            String typeCommade = request.getParameter("typeCommade");

            // Ces attributs sont utillisés pour définir les date min et max acessibles dans le calendrier
            request.getSession().setAttribute("dateMin", dateMin);
            request.getSession().setAttribute("dateMax", dateMax);

            // afficher tous les spectacles à partir du date d'aujourdhui
            if (typeCommade.equals("general")) {
                // Ces attributs sont utillisés pour afficher dans le calendrier les dernieres dates saisies en filtrage.
                request.getSession().setAttribute("dateMinFiltrage", dateMin);
                request.getSession().setAttribute("dateMaxFiltrage", dateMax);
                // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                
                List<SpectacleDatePhoto> lesSpectaclesFiltreDatePhoto = FiltrageDatePhoto(dateMin, dateMax, ds);
                
                request.getSession().setAttribute("listeSpectacles", lesSpectaclesFiltreDatePhoto);
                request.setAttribute("tousLesSpectacles", "tousLesSpectacles");
                this.getServletContext().getRequestDispatcher(vue).forward(request, response);
                
                // afficher les spectacles selon des critères de filtrage de date
            } else if (typeCommade.equals("filtrage")) {

                String dateMinFiltre = request.getParameter("dateMin");
                String dateMaxFiltre = request.getParameter("dateMax");

                request.getSession().setAttribute("dateMinFiltrage", dateMinFiltre);
                request.getSession().setAttribute("dateMaxFiltrage", dateMaxFiltre);
          
                   // Ajout pour utilisation AJAX               
                    List<SpectacleDatePhoto> lesSpectaclesFiltreDatePhoto = FiltrageDatePhoto(dateMinFiltre, dateMaxFiltre, ds);
                    request.getSession().setAttribute("listeSpectacles", lesSpectaclesFiltreDatePhoto);
                    this.getServletContext().getRequestDispatcher(vue).forward(request, response);
       
                
                // retour à la page précedente  
            } else {

                this.getServletContext().getRequestDispatcher(vue).forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServletSpectacles.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex.getMessage(), ex);
        }

    }

    public List<SpectacleDate> FiltrageDate(String date1, String date2, DataSource ds) throws SQLException { // date 1 sous forme yyyy-MM-dd
        List<SpectacleDate> lesSpectaclesFiltreDate;
        LocalDateTime ldtMin = LocalDateTime.parse(date1 + "T00:00");
        LocalDateTime ldtMax = LocalDateTime.parse(date2 + "T23:59:59");
        java.sql.Date dateMinFiltreSQL = new java.sql.Date(java.sql.Date.from(ldtMin.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        java.sql.Date dateMaxFiltreSQL = new java.sql.Date(java.sql.Date.from(ldtMax.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        lesSpectaclesFiltreDate = FiltreSpectacleDateDAO.FiltreSpectacleDate(ds, dateMinFiltreSQL, dateMaxFiltreSQL);
        return lesSpectaclesFiltreDate;

    }
    public List<SpectacleDatePhoto> FiltrageDatePhoto(String date1, String date2, DataSource ds) throws SQLException { // date 1 sous forme yyyy-MM-dd
        List<SpectacleDatePhoto> lesSpectaclesFiltreDatePhoto;
        LocalDateTime ldtMin = LocalDateTime.parse(date1 + "T00:00");
        LocalDateTime ldtMax = LocalDateTime.parse(date2 + "T23:59:59");
        java.sql.Date dateMinFiltreSQL = new java.sql.Date(java.sql.Date.from(ldtMin.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        java.sql.Date dateMaxFiltreSQL = new java.sql.Date(java.sql.Date.from(ldtMax.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        lesSpectaclesFiltreDatePhoto = FiltreSpectacleDateDAO.FiltreSpectacleDatePhoto(ds, dateMinFiltreSQL, dateMaxFiltreSQL);
        return lesSpectaclesFiltreDatePhoto;

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
