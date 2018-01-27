<%-- 
    Document   : detailSpectacle
    Created on : 15 févr. 2017, 09:47:18
    Author     : yassinea
--%>

<%@page import="Modeles.UtilisateurInfos"%>
<%@page import="Modeles.SpectacleDatePhoto"%>
<%@page import="Controleurs.ClasseHelp"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modeles.SpectacleDate"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Bienvenu sur Mytheâtre</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="Styles/StylesMyTheatre.css" rel="stylesheet" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

        <script src="scripts/MAJ_ElementsPage.js" type="text/javascript"></script>


    </head>
    <body>
        <script>
            function affichage() {

                $('#envoieInformationSpec').show();
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
                <nav>
                    <%-- le menu de navigation   --%>
                    <ul>
                        <li><a href="/Mytheatre/indexCtrler">Accueil</a></li>
                        <li><a href="/Mytheatre/Spectacle?typeCommade=general">Spectacle</a></li>
                        <li id="connexion"><%if (user != null) {%><a href="/Mytheatre/Deconnexion" >Deconnexion</a><%}%><%if (user == null) {%><a href="/Mytheatre/Connexion" >Connexion</a><%}%></li>
                    </ul>
                </nav> 
            </header>
            <div class="detailPage">
                <%-- Affichage les détails de chaque spectacle --%>
                <%
                    SpectacleDatePhoto spec = (SpectacleDatePhoto) request.getAttribute("spec");
                    List<Integer> listePlaces = (ArrayList<Integer>) request.getSession().getAttribute("listePlaces");
                %>
                <h2><%=spec.getNomSpectacle()%></h2>
                <div class="photoDetailSpectacle">
                    <img class="photoSpectacle" src="<%String url = "Images/spectacle0.jpg";
                        if (spec.getPhotos().size() > 0) {
                            url = spec.getPhotos().get(0).toString();
                        };%><%=url%>" width="300px" height="300px" onerror="imgError(this)"/>
                    <% if (spec.getPhotos().size() > 1) { %>
                    <div id="containerMiniPhotoDetail">
                        <% for (int i = 1; i < spec.getPhotos().size(); i++) {%>
                        <a href="<%= spec.getPhotos().get(i)%>"><img class="photoSpectacleMiniature" src="<%= spec.getPhotos().get(i)%>" onerror="imgError(this)"/>   </a>                     
                            <%}%>
                    </div>
                    <%} %>
                </div>
                <div id="detailSpectaclePhoto">
                    <% if (spec.getResumeSpectacle() == null) {%>
                    <label for="resumé"><strong>Résumé:</strong> </label> <span>pas de description disponible </span><br/>
                    <%} else {%>
                    <label for="resumé"><strong>Résumé: </strong></label> <span id="resume"><%=spec.getResumeSpectacle()%> </span><br/>
                    <%}%>

                    <label for="durée"><strong>Durée:</strong> </label> <span><%=spec.getDuree()%> minutes</span><br/><br/>
                    <hr>
                    <form id="dateRadio" action="spectacleAchat">
                        <div><% if (spec.getListeDate().size() != 1) {%><h2>Pour acheter des places, veuillez selectionner une date de représentation :</h2>
                            <% } else {%><h2>Ce spectacle sera disponible à la date suivante : </h2><% }%>
                        </div>
                        <%-- Recuperer les dates de chaque spectacles et le nombre de places disponibles pour chaque date et ensuite creer messages d'affichage  --%>
                        <% for (int i = 0; i < spec.getListeDate().size(); i++) {
                                String dateString = ClasseHelp.dateToString(spec.getListeDate().get(i));
                                int nbPlacesDispo = 400 - listePlaces.get(i);
                                String message = "";
                                String disable = "";
                                String couleurPlaceDispo = "";
                                String couleurRadioButon="";
                                if (nbPlacesDispo <= 70) {
                                    message += "Plus de places disponibles en ligne, veuillez vous adresser au guichet";
                                    disable += "disabled";
            
                                    couleurPlaceDispo += "color:red";
                                    
                                } else {
                                    message +=" "+(nbPlacesDispo - 70) + " place(s) disponible(s) en ligne";
                                    couleurPlaceDispo += "color:green";
                                }
                        %>
                        <input type="radio" name="date" value="<%=dateString%>" required
                               onclick="undisable('acheterplaces')" <% String checked = "";
                                   if (spec.getListeDate().size() == 1) {
                                       checked = "checked hidden";
                                   }%><%= checked%> <%= disable%>
                               <%-- String paramDate = request.getParameter("date");
                                   if (dateString.equals(paramDate)) {
                                       out.println("checked");
                                   }
                               --%>  
                               > 
                        <label for="date"><strong style=<%= couleurRadioButon%>>Représentation <% if (spec.getListeDate().size() != 1) {%><%=i + 1%><% }%> :</strong> </label> <span  style=<%= couleurRadioButon%>><%=dateString%> </span> 

                        <span style=<%= couleurPlaceDispo%>> <%=message%></span>
                        <br>
                        <% }%>
                        </div>

                        <%-- Le bouton Retour permet de revenir à la page précédente avec les parametres de filtrage dèja saisi --%>

                        <input type="submit" <%String disabled = "";
                            if (spec.getListeDate().size() != 1) {
                                disabled = "Class='disabled' ";
                            }%> <%= disabled%> value="Acheter Place(s)" name="Reserver" id="acheterplaces" <%String disabledAttribute = ""; if (spec.getListeDate().size() == 1 && (400 - listePlaces.get(0)) <= 70){disabledAttribute = "disabled";}%> <%=disabledAttribute%>>

                    </form></br></br>
                    <div id="emailBoxDetail">
                        <!--<input id="btEnvoiInfoSpec" type="button" value="Partager ces informations par mail" onclick="affichage()"> -->
                        <img id="btEnvoiInfoSpec" src="Images/email.png" alt="email" width="50px" title="Partager ces informations par mail" onclick="affichage()"/>
                        <div id="envoieInformationSpec" style=" display:none">

                            <form id="envoiMailDetail">
                                <label id="envoieMail">Saisissez votre mail</label></br>
                                <input id="tonEmail" type="email" name="textMail" required="" <%if (user != null) {%> value="<%=user.getMail()%>"<%}%>/></br>
                                <button id="mailBtn">Envoyer</button> 
                            </form>

                            <div id="reponseMail"></div>
                        </div>
                    </div>
                    <div class='retourBouton'>
                        <a href="/Mytheatre/Spectacle?typeCommade=retourAuFiltrage">Retour à la sélection</a><br>
                    </div>
                </div>
                <div id="spacer"></div>
                <footer>
                    <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
                </footer>
            </div>    
    </body>
</html>
