<%-- 
    Document   : producteur
    Created on : 9 mars 2017, 18:24:58
    Author     : niangd
--%>

<%@page import="Modeles.UtilisateurInfos"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Bienvenu sur Mytheâtre</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="scripts/MAJ_ElementsPage.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <link href="Styles/StylesMyTheatre.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="wrapper">

            <!-- l'en-tête de la page -->
            <header>
                <!-- la partie login : logo + login -->
                <% UtilisateurInfos user = (UtilisateurInfos)request.getSession().getAttribute("user");
                if (user != null ){%>
                <div id="logoCompteUtilisateur"> <a href="/Mytheatre/ConnexionValidation?connexionPseudo=<%=user.getPseudo()%>&connexionMotDePasse=<%=user.getPassword()%>" title="Mon Compte"><img src="Images/user.png" width="20px"/><span>  <%=user.getPseudo()%></span></a></div>
                    <%}%>
                <!-- le menu de navigation -->
                <nav>
                    <ul>
                        <li><a href="/Mytheatre/indexCtrler">Accueil</a></li>
                        <li><a href="/Mytheatre/Spectacle?typeCommade=general">Spectacle</a></li>
                        <li id="connexion"><%if (user != null ){%><a href="/Mytheatre/Deconnexion" >Deconnexion</a><%}%><%if (user == null ){%><a href="/Mytheatre/Connexion" >Connexion</a><%}%></li>
                    </ul>
                </nav>
            </header>
            <%
                int status = (int) request.getSession().getAttribute("status");
                String urlRetour;
                if (status == 1) {
                    urlRetour = "/Mytheatre/Retour?action=propositiongerant";
                } else {
                    urlRetour = "/Mytheatre/Retour?action=propositionprod";
                }
            %>       
            <div  id="gerant" class="boutonRetour">
                <a class="encours" href="<%=urlRetour%>">Retour</a>
            </div> 
            <div class="detailPage">
                <!-- le contenu de la page -->
                <form class="formulaireCentre" action="sendPropostionSpec">
                    <div>
                        <label>Titre du Spectacle : </label></br>
                        <input id="titrespectacle" class="inputADroite" type="text" name="titre" required /><span class="champObligatoire">*</span>
                    </div>
                    <div>
                        <label>Nom organisme : </label></br>
                        <input id="nomorganisme" class="inputADroite" type="text" name="organisme"/>
                    </div>
                    <div>
                        <label>Résumé : </label></br>
                        <input id="ajoutResume" class="inputADroite" type="text" name="resume"/>
                    </div>
                    <div>
                        <label>Prix unitaire : </label></br>
                        <input id="prixunitaire" class="inputADroite" type="text" name="prix" required/> <span class="champObligatoire">*</span>
                    </div>
                    <div>
                        <label>Durée en minutes: </label></br>
                        <input id="ajoutDuree" class="inputADroite" type="number" name="duree" step="1" min="1" max="999" required /><span class="champObligatoire">*</span>
                    </div>
                    <div>
                        <label>Complément d'informations: </label></br>
                        <input id="complementinfos" class="inputADroite" type="text" name="complementinfos" /> 
                    </div><br>
                    <hr>
                    <div class="dateDeRepresentation">
                        <label>Date(s) de représentation : </label><span class="champObligatoire">*</span></br>
                        <div id="toutesLesDates">
                            <div class="DateDeRepresentation">

                                <label class="dateNumber"><strong> 1 </strong></label>
                                <label>Date : </label>
                                <%      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date currentDate = new Date();
                                    String dateDuJour = dateFormat.format(currentDate);%>
                                <input class="ajoutDate" type="date" name="date1" min="<%=dateDuJour%>" value="<%=dateDuJour%>" required/>
                                <label>Heure : </label>
                                <input class="ajoutTime" type="time" name="time1" required/>
                            </div>
                            <div id="ajoutDeDateSup"></div>
                            <input name="nbDateTot" id="nbDateTot" value="1" hidden=""/>
                        </div>
                    </div>

                    <div class="boutonsPlusEtMoins">
                        <input type="button" class="ajouterDateOuPhoto" id="ajouterDate" onclick="ajouterDateSup()" value="+"/>
                        <input type="button" id="supprimerDate" class="supprimerDateOuPhoto" onclick="supprimerUneDate()" value="-" disabled/>
                        <label><em>Ajouter ou supprimer une date de représentation</em></label>
                    </div>
                    <hr>
                    <div class="centerStyle">
                        <input id="ajoutValider" type="submit" value="Envoyer votre proposition" />
                    </div>
                </form>
                <div class="centerStyle">
                    <span class="champObligatoire">(*) : Champs Obligatoires</span>
                </div>
            </div>     
            <%

                if (status == 1) {
                    urlRetour = "/Mytheatre/Retour?action=propositiongerant";
                } else {
                    urlRetour = "/Mytheatre/Retour?action=propositionprod";
                }
            %>       
            <div  id="gerant" class="boutonRetour">
                <a class="encours" href="<%=urlRetour%>">Retour</a>
            </div> 
            <div id="spacer"></div>
            <footer>
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>

    </body>
</html>



