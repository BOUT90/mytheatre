<%-- 
    Document   : CreationDeCompte
    Created on : 14 mars 2017, 10:49:36
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
                <div id="logoCompteUtilisateur"> <a href="/Mytheatre/ConnexionValidation?connexionPseudo=<%=user.getPseudo()%>&connexionMotDePasse=<%=user.getPassword()%>" title="Mon Compte"><img src="Images/user.png" width="20px"/><span>  <%=user.getPseudo()%></span></a></div>
                        <%}%>
                <!-- le menu de navigation -->
                <nav>
                    <ul>
                        <li><a href="index.html">Accueil</a></li>
                        <li><a href="/Mytheatre/Spectacle?typeCommade=general">Spectacle</a></li>
                        <li id="connexion"><%if (user != null) {%><a href="/Mytheatre/Deconnexion" >Deconnexion</a><%}%><%if (user == null) {%><a href="/Mytheatre/Connexion" >Connexion</a><%}%></li>
                    </ul>
                </nav>
            </header>
            <div class="detailPage">
                <!-- le contenu de la page -->
                <form class="formulaireCentre" action="ValidationCompteCree">
                    <div>
                        <label>Pseudo : </label>
                        <div id="bloc1">
                            <input id="pseudocreationcompte" oninput="creationCompte()" type="text" name="pseudocreationcompte" value="" size="40" required/>    
                            <span class="champObligatoire">*</span>
                        </div>
                        <div id="erro-login">${LoginexisteDeja}</div>
                    </div>
                    <div>
                        <label>Nom : </label>
                        <div id="bloc2">
                            <input id="nomcreationcompte" oninput="creationCompte()" type="text" name="nomcreationcompte" value="" size="40" required/>        
                            <span class="champObligatoire">*</span>
                        </div>

                    </div>
                    <div>
                        <label>Prenom : </label>
                        <div id="bloc3">
                            <input id="prenomcreationcompte" oninput="creationCompte()" type="text" name="prenomcreationcompte" value="" size="40"/>
                            <span class="champObligatoire">*</span>
                        </div>
                    </div>

                    <div>
                        <label>Adresse e-mail : </label>
                        <div id="bloc4">
                            <input id="adresseemailcreationcompte" oninput="creationCompte()" type="email" name="adresseemailcreationcompte" value="" size="40" required/>  
                            <span class="champObligatoire">*</span>
                        </div>
                    </div>
                    <div>
                        <label>Mot de passe : </label>
                        <div id="bloc5">
                            <input id="motdepassecreationcompte" onkeyup="validatePasswdCreationCompte()" type="password" name="motdepassecreationcompte" value="" size="40" required/>
                            <span class="champObligatoire">*</span>
                        </div>
                    </div>
                    <div>
                        <label> Confirmation : </label>
                        <div id="bloc6">
                            <input id="confmotdepassecreationcompte"  onkeyup="validatePasswdCreationCompte()"  type="password" name="motdepassecreationcompte" value="" size="40" required/>
                            <span class="champObligatoire">*</span>
                        </div>
                    </div>
                    <div id="passwordpasidentique"></div>
                    <div>
                        <label>Numéro de téléphone : </label>
                        <div id="bloc7">
                            <input id="numtelephonecreationcompte" type="text" name="numtelephonecreationcompte" value="" size="40"/>
                        </div>
                    </div>
                    <div>
                        <label>Rue : </label>
                        <div id="bloc8">
                            <input id="ruecreationcompte" type="text" name="ruecreationcompte" value="" size="40"/>
                        </div>
                    </div>
                    <div>
                        <label>Code postale : </label>
                        <div id="bloc9">
                            <input id="codepostalcreationcompte" type="text" name="codepostalcreationcompte" value="" size="40"/>
                        </div>
                    </div>
                    <div>
                        <label>Ville : </label>
                        <div id="bloc10">
                            <input id="villecreationcompte"  type="text" name="villecreationcompte" value="" size="40"/>
                        </div>
                    </div>

                    <div id="error-creationcompte">${ErrorModiCreationCompte}</div>
                    <div class="centerStyle">
                        <input id="validationcreationcompte" type="submit" value="Validation" disabled/>

                    </div>

                </form>
                <div class="centerStyle">
                    <a  class="boutonRetour" href="/Mytheatre/Retour?action=retourconection">Retour</a>
                </div>
            </div>

            <div id="spacer"></div>
            <footer>
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>

    </body>
</html>