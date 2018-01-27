<%-- 
    Document   : monCompteGerant
    Created on : 10 mars 2017, 11:00:22
    Author     : niangd
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
        <script src="scripts/TousLesChampsRemplis.js" type="text/javascript"></script>
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
            <div class="detailPage">
                <div class="formulaireCentre"  >

                    <div id="listeInfoCompte">                
                        <div class="centerStyle"><h2>Mon compte </h2><br></div>
                        <label>Login : </label><span style="font-weight: lighter;"><%=user.getPseudo()%></span><br>
                        <label>Nom : </label><span style="font-weight: lighter;"><%=user.getNom()%></span><br>
                        <label>Prenom : </label><span style="font-weight: lighter;"><%=user.getPrenom()%></span><br>
                        <label>E-mail : </label><span style="font-weight: lighter;"><% String mail = "non renseigné"; if (user.getMail() != null){mail = user.getMail();}%><%=mail%></span><br>
                        <label>Téléphone : </label><span style="font-weight: lighter;"><% String tel = "non renseigné"; if (user.getTel() != null){tel = user.getTel();}%><%=tel%></span><br>
                        <label>Adresse : </label><span style="font-weight: lighter;"><% String rue = "non renseignée"; if (user.getRue() != null){rue = user.getRue();}%><%=rue%></span><br>   
                        <label>Code postal: </label><span style="font-weight: lighter;"><% String codePostal = "non renseigné"; if (user.getCodepostal()!= null){codePostal = user.getCodepostal();}%><%=codePostal%></span><br>   
                        <label>Ville : </label><span style="font-weight: lighter;"><% String ville = "non renseignée"; if (user.getVille() != null){ville = user.getVille();}%><%=ville%></span>

                    </div>   


                    </br>
                    </br>
                    </br>

                    <div>
                        <a href="/Mytheatre/GerantGestionSpecCompte?choix=gestionSpectacle">Ajout Spectacle </a> <br>
                    </div>
                    <div>
                        <a href="/Mytheatre/GerantGestionSpecCompte?choix=proposerspectacle">Proposer Spectacle </a> <br>
                    </div>
                    <div>
                        <a href="/Mytheatre/GerantGestionSpecCompte?choix=changermotdepasse">Changement de mot de passe</a> <br>  
                    </div>
                    <div>
                        <a href="/Mytheatre/GerantGestionSpecCompte?choix=ChangementInfos">Changement informations</a> <br> <br> <br> 
                    </div>   

                </div>
                <div  id="gerant" class="boutonRetour">
                    <a href="/Mytheatre/Retour?action=retourconection">Changer utilisateur</a> <br>
                </div>
                <div id="spacer"></div>
                <footer>
                    <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
                </footer>
            </div>
        </div>

    </body>
</html>


































