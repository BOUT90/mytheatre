/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author niangd
 */
@WebServlet(name = "Retour", urlPatterns = {"/Retour"})
public class Retour extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        String valeur = request.getParameter("action");

        switch (valeur) {

            case "changementmodepasseprod":

                request.getRequestDispatcher("/WEB-INF/ProdPropoSpecGestCompte.jsp").forward(request, response);
                break;
            case "retourchangementinfosProd":

                request.getRequestDispatcher("/WEB-INF/ProdPropoSpecGestCompte.jsp").forward(request, response);
                break;
            case "propositionprod":

                request.getRequestDispatcher("/WEB-INF/ProdPropoSpecGestCompte.jsp").forward(request, response);
                break;

            case "gesttionspect":

                request.getRequestDispatcher("/WEB-INF/GerantGestionSpecCompte.jsp").forward(request, response);
                break;

            case "gestiongerant":

                request.getRequestDispatcher("/WEB-INF/GerantGestionSpecCompte.jsp").forward(request, response);
                break;
            case "retourchangementinfosGerant":

                request.getRequestDispatcher("/WEB-INF/GerantGestionSpecCompte.jsp").forward(request, response);
                break;
            case "propositiongerant":

                request.getRequestDispatcher("/WEB-INF/GerantGestionSpecCompte.jsp").forward(request, response);
                break;
            case "changementmodepassegerant":

                request.getRequestDispatcher("/WEB-INF/GerantGestionSpecCompte.jsp").forward(request, response);
                break;
            case "retourconection":
                HttpSession session = request.getSession();
                 session.removeAttribute("user");
                request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
                break;

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
