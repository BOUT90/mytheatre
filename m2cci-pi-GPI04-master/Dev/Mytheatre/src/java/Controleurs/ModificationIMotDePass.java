/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.GetMotDePasseDAO;
import DAO.SetMotDepassDAO;
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
@WebServlet(name = "ModificationInformations", urlPatterns = {"/ModificationMotDePass"}) 
public class ModificationIMotDePass extends HttpServlet {

    @Resource(name = "jdbc/UFRIMA")
    private DataSource ds;
    private Object SetMotDepassProdDAO;

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
        boolean retourVersPageConnexionApreschmotdePass = false;
        //String motdepass = (String) request.getSession().getAttribute("motDePass");
        String login = (String) request.getSession().getAttribute("login");
        String ancienmotdepass = request.getParameter("ancienmotdepasse");
        String nouvmotdepass = request.getParameter("ancienConnexionMotDePasse");
        String confirmmotdepass = request.getParameter("confirmconnexionMotDePasse");

        try {
            HttpSession session = request.getSession();
            retourVersPageConnexionApreschmotdePass = true;
            request.setAttribute("retourVersPageConnexionApreschmotdePass", retourVersPageConnexionApreschmotdePass);
            if (ancienmotdepass.equals(GetMotDePasseDAO.GetMotDePasse(ds, login))) {
                SetMotDepassDAO.SetMotDepass(ds, nouvmotdepass, login);
                session.removeAttribute("user");
                request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);

            } else {
                request.setAttribute("motdepassincorrect", "Votre mot de pass est incorrect");
                request.getRequestDispatcher("/WEB-INF/chagementMotDePasse.jsp").forward(request, response);
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
