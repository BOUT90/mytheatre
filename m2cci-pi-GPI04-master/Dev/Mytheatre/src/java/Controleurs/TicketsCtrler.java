package Controleurs;

import Modeles.SpectacleDate;
import Modeles.TicketPDF;
import Modeles.TicketRecap;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import im2ag.m2pcci.maildemo.mail.MailSender;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 * Cette servlet, réalise l'action associée au bouton acheter du formulaire
 * "d'achat" défini dans la page index.html. Elle récupère les paramètres du
 * formulaire (nom, date de l'épreuve, nombre de place, identité et email de
 * l'utilisateur), génère un billet au format pdf qu'elle envoie par email à
 * l'acheteur, ensuite la servlet redirige la requête vers une page jsp afin de
 * confirmer "l'achat" et l'envoi du courriel ou d'afficher un message d'erreur
 * en cas de problème.
 *
 * @author Philippe Genoud (Université Grenoble Alpes - laboratoire LIG STeamer)
 *
 */
@WebServlet(name = "TicketsCtrler", urlPatterns = {"/sendTicket"})

public class TicketsCtrler extends HttpServlet {

    /**
     * l'objet mailSession est une ressource gérée par le conteneur de servlets.
     * Il est défini dans le fichier de configuration context.xml et dans le
     * fichier web.xml. L'annotation ci-dessous permet de récupérer sa
     * référence.
     */
//    @Resource(name = "mail/DEMO")
//    private Session mailSession;
    /* 
     cela serait mieux de procéder avec une ressource gérée par le serveur
     mais cela impose d'avoir le droit de copier la fichier javax.mail.jar dans
     le lib du serveur tomcat. Mais sur les machines de l'ufr les étudiants n'ont
     pas les droits adminsitrateur, et ecrire ce fichier dans le répertoire 
     nblib, de .netbeans ne suffit pas.
     C'est pourquoi l'objet mailSession est configuré avec des paramètres définis
     comme paramètres d'initialisation de la servlet, dans le fichier web.xml.
     */
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
        response.setContentType("text/html");
        //----------------------------------------------------------------------
        // recupération des pramètres de la requête
        //----------------------------------------------------------------------
        String recipientEMail = request.getParameter("textMail");
        String nom = request.getParameter("textNom");
        
        //String titulaire = request.getParameter("titulaireBillet");
        //int nbTickets = Integer.parseInt(request.getParameter("nbTickets"));
        //int idEpreuve = Integer.parseInt(request.getParameter("epreuve"));

        // on récupère l'objet Epreuve représentant l'épreuve d'identifiant idEpreuve
        //Epreuve ep = EpreuveDAO.getEpreuve(idEpreuve);
        //----------------------------------------------------------------------
        // génération du ticket au format pdf et envoi par couriel de ce fichier
        // à l'adresse email de l'utilisateur telle que définie dans le formulaire
        //----------------------------------------------------------------------
        boolean ticketEnvoye = false;
        try {
            //------------------------------------------------------------------
            // construction du fichier pdf correspondant au ticket
            //------------------------------------------------------------------
            // récupération le chemin absolu de l'image du logo
            ServletContext cntx = getServletContext();
            //String logoImagePath = cntx.getRealPath("/images/logo.png");

            List<TicketRecap> listeTickets = (List<TicketRecap>) request.getSession().getAttribute("listeTickets");
            SpectacleDate spec = (SpectacleDate) request.getSession().getAttribute("spectacle");
            String date = (String) request.getSession().getAttribute("date");
            int noDossier = (Integer) request.getSession().getAttribute("noDossier");
            int prixTotal = (Integer) request.getSession().getAttribute("prixTotal");
            float remise = (float) request.getSession().getAttribute("remise");


            byte[] pdfTicket = TicketPDF.createPDF_AsByteArray(listeTickets,spec, date, noDossier, prixTotal,remise );

            //-----------------------------------------------------------------------
            // envoi du courriel avec comme document attaché le fichier pdf du ticket
            //-----------------------------------------------------------------------
            // création de l'objet mail session, ce code pourrait être avantageusement
            // remplacé par 
            //    @Resource(name = "mail/DEMO")
            //    private Session mailSession;
            // voir au debut de la servlet, mais pour des raison de configuration
            // (pas les droits administrateur) pour les étudiants ce n'est pas possible
            // au niveau des machine de l'ufr.
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", getInitParameter("smtp_server")); // smtps.ujf-grenoble.fr
            props.put("mail.smtp.port", getInitParameter("smtp_port")); // 587
            Session mailSession = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(getInitParameter("mail_user_name"), getInitParameter("mail_user_passwd"));
                        }
                    });

            // création du message
            String messageBody = "MyTheatre vous remercie\n"
                    + "voici en document attaché le récapitulatif de votre achat. ";
            MailSender.sendMailWithAttachedFile(mailSession,
                    getInitParameter("sender"),
                    recipientEMail,
                    getInitParameter("title"),
                    messageBody,
                    "ticket.pdf",
                    pdfTicket,
                    "application/pdf");
            ticketEnvoye = true;
            //----------------------------------------------------------------------
            // redirection vers la page jsp appropriée
            // //----------------------------------------------------------------------
            //request.setAttribute("epreuve", ep);
            try (PrintWriter out = response.getWriter()) {
                out.println("<span style='color:green'>Message envoyé</span>");
            }
        } catch (WriterException ex) {
            // le ticket n'a pas été envoyé
            // un message d'erreur sera envoyé à l'utilisateur
            throw new ServletException(ex.getMessage(), ex);
        } catch (MessagingException ex) {
            Logger.getLogger(TicketsCtrler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<span style='color:red'>Mail éronné</span>");
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
