/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.ComparLoginDAO;
import DAO.CreationCompteDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.sql.DataSource;
import org.bouncycastle.asn1.isismtt.ocsp.RequestedCertificate;

/**
 *
 * @author niangd
 */
@WebServlet(name = "ValidationCompteCree", urlPatterns = {"/ValidationCompteCree"})
public class ValidationCompteCree extends HttpServlet {

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

        String pseudo = request.getParameter("pseudocreationcompte");
        String nom = request.getParameter("nomcreationcompte");
        String prenom = request.getParameter("prenomcreationcompte");
        String email = request.getParameter("adresseemailcreationcompte");
        String numtelephone = request.getParameter("numtelephonecreationcompte");
        String motdepasse = request.getParameter("motdepassecreationcompte");
        String rue = request.getParameter("ruecreationcompte");
        String codepostal = request.getParameter("codepostalcreationcompte");
        String ville = request.getParameter("villecreationcompte");

        try {
            if (ComparLoginDAO.ComparLogin(ds, pseudo)) {

                request.setAttribute("LoginexisteDeja", "Ce login existe déjà. Veuillez choisir un autre login.");
                request.getRequestDispatcher("/WEB-INF/CreationDeCompte.jsp").forward(request, response);
                
            } else {
                if (CreationCompteDAO.CreationCompte(ds, motdepasse, pseudo, nom, prenom, numtelephone, rue, ville, email, codepostal)) {
                    request.getRequestDispatcher("/WEB-INF/MessageCreationCompte.jsp").forward(request, response);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ValidationCompteCree.class.getName()).log(Level.SEVERE, null, ex);
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
