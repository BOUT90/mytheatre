/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.InfosUtilisateurDAO;
import DAO.VerficationConnectionDAO;
import Modeles.UtilisateurInfos;
import java.io.IOException;
import java.sql.SQLException;
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
@WebServlet(name = "ServletValidationConnexion", urlPatterns = {"/ConnexionValidation"})
public class ServletValidationConnexion extends HttpServlet {

    @Resource(name = "jdbc/UFRIMA")
    private DataSource dataSource;

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
        String login = request.getParameter("connexionPseudo");
        String motDePass = request.getParameter("connexionMotDePasse");

        HttpSession session = request.getSession();

        session.setAttribute("login", login);
        session.setAttribute("motDePass", motDePass);

        int status;

        try {

            status = VerficationConnectionDAO.VerficationConnection(dataSource, login, motDePass);

            if (status == 1) {
                session.setAttribute("status", status);
                UtilisateurInfos user = InfosUtilisateurDAO.InfosUtilisateur(dataSource, login);
               // session.setAttribute("MessageApresModifCompte", " Vos modifications ont été prises en compte");
                session.setAttribute("user", user);
                this.getServletContext().getRequestDispatcher("/WEB-INF/GerantGestionSpecCompte.jsp").forward(request, response);

            } else if (status == 0) {
                session.setAttribute("status", status);
                //request.getSession().setAttribute("login", login);
                UtilisateurInfos user = InfosUtilisateurDAO.InfosUtilisateur(dataSource, login);
                session.setAttribute("user", user);
                this.getServletContext().getRequestDispatcher("/WEB-INF/ProdPropoSpecGestCompte.jsp").forward(request, response);

            } else {
                request.setAttribute("loginPassWordError", "Votre login ou votre mot de pass est incorrect");
                this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);

            }

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
