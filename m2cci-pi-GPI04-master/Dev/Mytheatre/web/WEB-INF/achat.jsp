<%-- 
    Document   : achat
    Created on : 2 mars 2017, 16:57:05
    Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>

<%@page import="Modeles.UtilisateurInfos"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="Controleurs.ClasseHelp"%>
<%@page import="java.util.List"%>
<%@page import="Modeles.TicketRecap"%>
<%@page import="Modeles.SpectacleDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="Styles/StylesMyTheatre.css" rel="stylesheet" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="scripts/MAJ_ElementsPage.js" type="text/javascript"></script>
        <title>JSP Page</title>
    </head>
    <body>

        <script>
            function affichage() {

                $('#divMail').show();
            }
            ;
        </script>

        <div id="wrapper">
            <header>
                <!-- la partie login : logo + login -->
                <% UtilisateurInfos user = (UtilisateurInfos) request.getSession().getAttribute("user");
                    if (user != null) {%>
                <div id="logoCompteUtilisateur"> <a href="/Mytheatre/ConnexionValidation?connexionPseudo=<%=user.getPseudo()%>&connexionMotDePasse=<%=user.getPassword()%>" title="Mon Compte"><img src="Images/user.png" width="20px"/><span>  <%=user.getPseudo()%></span></a></div>
                        <%}%>
                <!-- le menu de navigation -->
                <nav>
                    <ul>
                        <li><a href="/Mytheatre/indexCtrler">Accueil</a></li>
                        <li><a href="/Mytheatre/Spectacle?typeCommade=general">Spectacle</a></li>
                        <li id="connexion"><%if (user != null) {%><a href="/Mytheatre/Deconnexion" >Deconnexion</a><%}%><%if (user == null) {%><a href="/Mytheatre/Connexion" >Connexion</a><%}%></li>
                    </ul>
                </nav>
            </header>
            <div class="detailPage">
                <h1 class="centerStyle" >Merci de votre achat</h1>
                <div id="recapAchat">
                    <p>
                        <% SpectacleDate spec = (SpectacleDate) request.getSession().getAttribute("spectacle");
                            String date = (String) request.getSession().getAttribute("date");
                            
                            List<TicketRecap> listeTickets = (List<TicketRecap>) request.getSession().getAttribute("listeTickets");%>

                        <b>Représentation:</b> <%=spec.getNomSpectacle()%>: <%=date%>
                    </p>

                    <% int noDossier = (Integer) request.getSession().getAttribute("noDossier");
                        int prixTotal = (Integer) request.getSession().getAttribute("prixTotal");
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                        Date dateCurrent = new Date();
                        String dateStr = dateFormat.format(dateCurrent);
                    %>
                    <p><b>Numero Dossier: </b><%= noDossier%></p>
                    <p><b>Date d'achat: </b><%=dateStr%></p> 
                </div>

                <table id="tableTickets">  
                    <caption id="cap"><%String str;
                        if (listeTickets.size() == 1) {
                            str = "Ticket acheté";
                        } else {
                            str = listeTickets.size()+ " tickets achetés";
                        }%><%=str%>
                    </caption>
                    <tr>
                        <td> <strong>Place</strong></td>
                        <td> <strong>Rang</strong></td>
                        <td> <strong>Zone</strong></td>
                        <td> <strong>Prix</strong></td>
                    </tr>
                    <% for (int i = 0; i < listeTickets.size(); i++) {
                    %>
                    <tr>
                        <td><%= listeTickets.get(i).getNoPlace()%></td>
                        <td><%= listeTickets.get(i).getNoRang()%></td>
                        <td><%= listeTickets.get(i).getNomC()%></td>
                        <td><%= listeTickets.get(i).getPrix()%>€</td>
                    </tr>
                    <% }%>
                </table>

                <div id="prixtotal"> 

                    <% float remise = 0;
                        if (listeTickets.size() >= 10) {
                            remise = prixTotal * 25 / 100;
                        }
                        request.getSession().setAttribute("remise", remise);%>

                    <% if (remise == 0) {%>
                    <p><b>Prix Total: </b><%= prixTotal%>€</p> </br>
                    <%} else {%>
                    <p><b>Remise: </b><%=remise%>€</p> </br>
                    <p><b>Prix total avec remise: </b><%=prixTotal - remise%>€</p> </br>
                    <%}%>
                    <a href="/Mytheatre/ServletCreationPDF" target="blank" >Afficher votre achat en format PDF</a>
                    <div class="centerStyle">
                        <div id="emailBoxAchat">
                            <img id="tonBouton" src="Images/email.png" alt="email" width="50px" title="Recevoir cet achat par mail" onclick="affichage()"/>
                            <!--<input id="tonBoutton" type="button" value="Envoyer mail" onclick="affichage()"> -->

                            <div id="divMail" style=" display:none">
                                <form id="envoiMailAchat">
                                    <!--<label >Saississez votre nom</label></br>
                                    <input type="text" name="textNom" value=""/><br>-->
                                    <label >Saisissez votre mail</label></br>

                                    <input id="tonEmail" type="email" name="textMail" required="" <%if (user != null) {%> value="<%=user.getMail()%>"<%}%>/></br>
                                    <button id="mailBtnAchat">Envoyer</button> 
                                    
                                </form>
                            </div>
                            <div id="reponseMail"></div>
                        </div>
                    </div>
                    <%-- <div id="divMail" style=" display:none">
                       <form action="sendPropostionSpec">
                           <label id=""envoieMail>Saississez votre nom</label>
                           <input type="text" name="textNom" value=""/><br>
                           <label id=""envoieMail>Saississez votre mail</label>
                           <input type="text" name="textMail" required=""/>
                           <input type="submit" value="Envoyer"/> 
                       </form>
                   </div>--%>


                </div>
            </div>   
            <div id="spacer"></div>
            <footer>
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>

    </body>

</html>
