<%-- 
    Document   : connexion
    Created on : 9 mars 2017, 13:21:37
    Author     : lagierg
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
            <div class="boutonRetour">
                <a class="encours" href="/Mytheatre/Retour?action=gesttionspect">Retour</a>
            </div>
            <div class="detailPage">
                <!-- le contenu de la page -->
                <form class="formulaireCentre" action="AjoutSpectacle">
                    <div>
                        <label>Nom du Spectacle : </label></br>
                        <input id="ajoutTitre" class="inputADroite" type="text" name="nom" required oninput="ajoutPossible('ajoutValider')"/><span class="champObligatoire">*</span>
                    </div>
                    <div>
                        <label>Résumé : </label></br>
                        <input id="ajoutResume" class="inputADroite" type="text" name="resume"/>
                    </div>
                    <div>
                        <label>Durée en minutes: </label></br>
                        <input id="ajoutDuree" class="inputADroite" type="number" name="duree" step="1" min="1" max="999" required oninput="ajoutPossible('ajoutValider')"/><span class="champObligatoire">*</span>
                    </div>
                    <hr>
                    <div class="dateDeRepresentation">
                        <label>Date(s) de représentation : </label><span class="champObligatoire">*</span></br>
                        <div id="toutesLesDates" class="centerStyle">
                            <div class="DateDeRepresentation">

                                <label class="dateNumber"><strong> 1 </strong></label>
                                <label>Date : </label>
                                <%      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date currentDate = new Date();
                                    String dateDuJour = dateFormat.format(currentDate);%>
                                <input class="ajoutDate" type="date" oninput="ajoutPossible('ajoutValider')" name="date1" min="<%=dateDuJour%>" value="<%=dateDuJour%>" required/>
                                <label>Heure : </label>
                                <input class="ajoutTime" type="time" name="time1" required oninput="ajoutPossible('ajoutValider')"/>
                            </div>
                            <div id="ajoutDeDateSup"></div>
                            <input name="nbDateTot" onchange="ajoutPossible('ajoutValider')" id="nbDateTot" value="1" hidden=""/>
                        </div>
                    </div>

                    <div class="boutonsPlusEtMoins">
                        <input type="button" class="ajouterDateOuPhoto" id="ajouterDate" onclick="ajouterDateSup()" value="+"/>
                        <input type="button" id="supprimerDate" class="supprimerDateOuPhoto" onclick="supprimerUneDate()" value="-" disabled/>
                        <label><em>Ajouter ou supprimer une date de représentation</em></label>
                    </div>
                    <hr>
                    <div class="dateDeRepresentation">
                        <label>Photo(s) : <em>url (1) pour l'affiche</em></label>
                        <div class="centerStyle">
                            <div id="toutesLesPhotos">
                                <div class="ulrPhotoEtApercu">
                                    <label class="photoNumber"><strong> 1 </strong></label>
                                    <label>URL photo : </label>
                                    <input class="urlPhoto" id="urlPhoto1" type="text" name="urlPhoto1" oninput="apercuMiniPhoto(1)"/><img class="photoApercu" id="photoApercu1" src="Images/spectacle0.jpg" width="50px" heigh="50px" onerror="imgError(this)">
                                </div>


                                <div id="ajoutDePhotoSup"></div>
                                <input name="nbPhotoTot" id="nbPhotoTot" value="1" hidden=""/>
                            </div>
                        </div>
                    </div>
                    <div class="boutonsPlusEtMoins">
                        <input type="button" class="ajouterDateOuPhoto" id="ajouterPhoto" onclick='ajouterPhotoSup()' value="+"/>
                        <input type="button" id="supprimerPhoto" class="supprimerDateOuPhoto" onclick="supprimerUnePhoto()" value="-" disabled/>
                        <label><em>Ajouter ou supprimer une photo</em></label>
                    </div>
                    <hr>
                    <div class="centerStyle">
                        <div style='margin:5px'>
                            <input id="checkboxRequired" onclick="ajoutPossible('ajoutValider')" type='checkbox' required/><span> J'ai terminé la saisie du formulaire </span>
                        </div>
                        <input class="disabled" id="ajoutValider" type="submit" value="Ajouter Le Spectacle Et Ses Représentations" />
                    </div>
                </form>
                <div class="centerStyle">
                    <span class="champObligatoire">(*) : Champs Obligatoires</span>
                </div>
            </div>
            <div class="boutonRetour">
                <a class="encours" href="/Mytheatre/Retour?action=gesttionspect">Retour</a>
            </div>
            <div id="spacer"></div>
            <footer>
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>

    </body>
</html>



