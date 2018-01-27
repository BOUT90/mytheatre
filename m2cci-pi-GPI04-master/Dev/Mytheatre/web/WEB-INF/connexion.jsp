<%-- 
    Document   : connexion
    Created on : 9 mars 2017, 13:21:37
    Author     : lagierg
--%>

<%@page import="Modeles.UtilisateurInfos"%>
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
        <link href="Styles/StylesMyTheatre.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="wrapper">

            <!-- l'en-tête de la page -->
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
            <%
                if (request.getAttribute("retourVersPageConnexionApreschmotdePass") != null) {%>
            <div class="centerStyle" style="color:green;"> 
                <p> Votre mot de pass a été bien modifié, veuillez vous reconnecter</p>
                
            </div>

            <%}%>                

            <div class="detailPage">
                <!-- le contenu de la page -->
                <form class="formulaireCentre" action="ConnexionValidation">
                    <div>
                        <label>Login : </label>
                        <input id="connexionInput1" oninput="connexionPossible()" type="text" name="connexionPseudo"/>
                    </div>
                    <div>
                        <label>Mot De Passe : </label>
                        <input id="connexionInput2" oninput="connexionPossible()" type="password" name="connexionMotDePasse"/>
                        <div id="error-LoginEtOuPassword">${loginPassWordError}</div>
                    </div>
                    <div class="centerStyle">
                        <input id="connexionInput3" type="submit" value="Connexion" disabled/>
                    </div>
                </form>
                <div style="text-align: center; margin-top: 5px"><a href="/Mytheatre/CreationDeCompte?">Pas encore de compte ? Créer votre compte</a></div>
            </div>
            <div id="spacer"></div>
            <footer>
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>

    </body>
</html>



