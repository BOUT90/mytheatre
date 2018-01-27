<%-- 
    Document   : gestionInfosProd
    Created on : 10 mars 2017, 10:37:30
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
        <script src="scripts/MAJ_ElementsPage.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="wrapper">

            <!-- l'en-tête de la page -->
            <header>
                <!-- la partie login : logo + login -->
                <% UtilisateurInfos user = (UtilisateurInfos) request.getSession().getAttribute("user");
                    if (user != null) {%>
                <div id="logoCompteUtilisateur"> <a href="/Mytheatre/ConnexionValidation?connexionPseudo=<%=user.getPseudo()%>&connexionMotDePasse=<%=user.getPassword()%>" title="Mon Compte">
                        <img src="Images/user.png" width="20px"/><span>  <%=user.getPseudo()%></span></a></div>
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
                <!-- le contenu de la page -->
                <form class="formulaireCentre" action="ModificationInfos">
                    <%--<div>
                        <label>Pseudo : </label>
                        <input id="changementinfo1" oninput="changementInfos()" type="text" name="connexionPseudo" value="<%=user.getPseudo()%>"/>
                    </div>--%>
                    <div>
                        <label>Nom : </label>
                        <input id="changementinfo2" oninput="changementInfos()" type="text" name="nom" value="<%=user.getNom()%>" required/>
                        <span class="champObligatoire">*</span>
                    </div>
                    <div>
                        <label>Prenom : </label>
                        <input id="changementinfo3" oninput="changementInfos()" type="text" name="prenom" value="<%=user.getPrenom()%>" required/>
                        <span class="champObligatoire">*</span>
                    </div>
                    <div>
                        <label>Adresse e-mail : </label>
                        <input id="changementinfo4" oninput="changementInfos()" type="email" name="adresseemail" value="<%=user.getMail()%>" required/>
                        <span class="champObligatoire">*</span>
                    </div>          
                    <div>
                        <label>Téléphone : </label>
                        <input id="changementinfo5"   type="text" name="telephone" value="<% String tel = ""; if (user.getTel() != null){tel = user.getTel();}%><%=tel%>"/>
                    </div>   
                    <div>
                        <label>Rue : </label>
                        <input id="changementinfo6"  type="text" name="rue" value="<% String rue = ""; if (user.getRue() != null){rue = user.getRue();}%><%=rue%>"/>
                    </div>
                    <div>
                        <label>Code postal : </label>
                        <input id="changementinfo7" type="text" name="codepostal" value="<% String codePostal = ""; if (user.getCodepostal()!= null){codePostal = user.getCodepostal();}%><%=codePostal%>"/>
                    </div>
                    <div>
                        <label>Ville : </label>
                        <input id="changementinfo8" type="text" name="ville" value="<% String ville = ""; if (user.getVille()!= null){ville = user.getVille();}%><%=ville%>"/>
                    </div>
                    <div id="error-ModiInfos">${ErrorModiCompte}</div>
                    <div class="centerStyle">
                        <input id="changementinfo9" type="submit" value="Validation" />
                    </div>
                </form>
            </div>
            <%
                int status = (int) request.getSession().getAttribute("status");
                String urlRetour;
                if (status == 1) {
                    urlRetour = "/Mytheatre/Retour?action=retourchangementinfosGerant";
                } else {
                    urlRetour = "/Mytheatre/Retour?action=retourchangementinfosProd";
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

