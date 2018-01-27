/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.InfosUtilisateurDAO;
import Modeles.UtilisateurInfos;
import java.io.IOException;
import java.sql.SQLException;
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
 * @author niangd
 */
@WebServlet(name = "GestionSpecatcleInfosGerant", urlPatterns = {"/GerantGestionSpecCompte"}) 
public class GerantGestionSpecCompte extends HttpServlet {

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
        String choix = request.getParameter("choix");
        switch (choix) {
            case "gestionSpectacle":

                request.getRequestDispatcher("/WEB-INF/ajoutSpectacle.jsp").forward(request, response);

                break;
            case "proposerspectacle":

                request.getRequestDispatcher("/WEB-INF/producteur.jsp").forward(request, response);

                break;

            case "changermotdepasse":
                //request.setAttribute("motdepasse", motDePasse);
                request.getRequestDispatcher("/WEB-INF/chagementMotDePasse.jsp").forward(request, response);

                break;

                case "ChangementInfos":
                    // request.getRequestDispatcher("/WEB-INF/ChangementInfos.jsp").forward(request, response);
                

                try {
//                     String pseudo="";
//                     String nom ="";
//                     String prenom="";
//                     String email="";
                    String login = (String) request.getAttribute("login");
                    UtilisateurInfos Utilisateur = InfosUtilisateurDAO.InfosUtilisateur(ds, login);
                    
                    HttpSession session = request.getSession();
                    String email=Utilisateur.getMail();
                    
                    session.setAttribute("pseudo", Utilisateur.getPseudo());
                    session.setAttribute("nom", Utilisateur.getNom());
                    session.setAttribute("prenom", Utilisateur.getPrenom());
                    session.setAttribute("email", Utilisateur.getMail());

                    //request.setAttribute("motdepasse", motDePasse);
                    request.getRequestDispatcher("/WEB-INF/ChangementInfos.jsp").forward(request, response);

                    break;
                } catch (SQLException ex) {
                    Logger.getLogger(ProdPropoSpecGestCompte.class.getName()).log(Level.SEVERE, null, ex);
                }

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
