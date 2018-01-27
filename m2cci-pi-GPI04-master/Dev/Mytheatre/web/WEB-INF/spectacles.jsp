<%-- 
    Document   : Spectacles
    Created on : 14 févr. 2017, 16:04:46
    Author     : yassinea
--%>
<%@page import="Modeles.UtilisateurInfos"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="Controleurs.ClasseHelp"%>
<%@page import="DAO.DateFinaleDAO"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="Modeles.SpectacleDatePhoto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Bienvenu sur Mytheâtre</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link href="Styles/StylesMyTheatre.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="scripts/MAJ_ElementsPage.js" type="text/javascript"></script>

        <!--   
         <script src="//code.jquery.com/jquery-3.1.0.min.js"></script>
        <script src="scripts/filtreSpectacleDateParAJAX.js" type="text/javascript"></script>
        -->

        <script src="scripts/MAJ_ElementsPage.js" type="text/javascript"></script>
    </head>
    <body  id="choix">
        <div id="wrapper">
            <header>
                                <!-- la partie login : logo + login -->
                <% UtilisateurInfos user = (UtilisateurInfos)request.getSession().getAttribute("user");
                if (user != null ){%>
                <div id="logoCompteUtilisateur"> <a href="/Mytheatre/ConnexionValidation?connexionPseudo=<%=user.getPseudo()%>&connexionMotDePasse=<%=user.getPassword()%>" title="Mon Compte"><img src="Images/user.png" width="20px"/><span>  <%=user.getPseudo()%></span></a></div>
                    <%}%>
                <nav>
                    <ul>
                        <li><a href="/Mytheatre/indexCtrler">Accueil</a></li>
                        <li><a href="/Mytheatre/Spectacle?typeCommade=general" class="encours" >Spectacle</a></li>
                        <li id="connexion"><%if (user != null ){%><a href="/Mytheatre/Deconnexion" >Deconnexion</a><%}%><%if (user == null ){%><a href="/Mytheatre/Connexion" >Connexion</a><%}%></li>
                    </ul>
                </nav>    
            </header>
            <div class="detailPage">
                <div class="filtrageSpectacle">
                    <%-- Ce code permettra d'interroger la base de données pour récuperer les types de spectacles et de remplir la liste déroulante
                    Ce code n'est pas encore pas encore en connexion avec la base --%>
                    <hr>
                    <form id="choixDate">
                        <% String Min = (String) request.getSession().getAttribute("dateMin");
                            String Max = (String) request.getSession().getAttribute("dateMax");
                        %>
                        <% String MinAff = (String) request.getSession().getAttribute("dateMinFiltrage");
                            String MaxAff = (String) request.getSession().getAttribute("dateMaxFiltrage");
                        %>
                        <%--onchange='MAJ_Date()'--%>
                        <span><strong> Recherche par date du : </strong></span>
                        <input name="dateMin" type="texte" id="datePicker1" value="  Entrez une date ici"/>
                        <span><strong>au : </strong></span>
                        <input name="dateMax" type="texte" id="datePicker2" />



                        <select id="typeSpectacleSelect" name="typeSpectacle" class="select">

                            <% List<String> listeTypes = new ArrayList<String>();
                                listeTypes.add("tous les types");
                                listeTypes.add("tout public");
                                listeTypes.add("humour");
                                listeTypes.add("enfant");
                                listeTypes.add("politique");
                                for (int i = 0; i < listeTypes.size(); i++) {
                                    String type = listeTypes.get(i);
                            %>
                            <option value="<%=type%>"><%=type%> </option>
                            <% }%>
                            <input  type="hidden" name="typeCommade" value="filtrage" />
                        </select>
                            <div class="clearMiniFiche"></div>
                    </form>
                    <%--
                     <input type="submit" value="consulter" name="consulter" />
                    --%>
                    <hr>
                </div>
                <%-- Affichage la liste de spectacles venant de la base de données selon les critères  --%>
                <div id="spectaclesResultats">
                    <% List<SpectacleDatePhoto> listeSpectacleDatePhoto = (List<SpectacleDatePhoto>) request.getSession().getAttribute("listeSpectacles");
                        Integer nombreSpe = listeSpectacleDatePhoto.size();%>
                    <%  String tousLesSpectacles = (String) request.getAttribute("tousLesSpectacles");
                        boolean tousLesSpectaclesBool = false;
                        if (tousLesSpectacles != null) {
                            tousLesSpectaclesBool = tousLesSpectacles.equals("tousLesSpectacles");
                        }
                        if (tousLesSpectaclesBool) {%>
                    <h2> L'ensemble des spectacles proposés : </h2> <%}%>
                    <% if (nombreSpe == 0 && !tousLesSpectaclesBool) {%>
                    <h2> Désolé! Aucun spectacle n'a été trouvé pour les dates et le type sélectionnés.</h2><br/>
                    <% }%>
                    <%if (nombreSpe == 1 && !tousLesSpectaclesBool) {%>
                    <h2> <%=nombreSpe%> spectacle a été trouvé pour les dates et le type sélectionnés.</h2><br/>
                    <% }%>

                    <%if (nombreSpe > 1 && !tousLesSpectaclesBool) {%>
                    <h2> <%=nombreSpe%> spectacles ont été trouvés pour les dates et le type sélectionnés.</h2><br/>
                    <% }%>
                    <%for (SpectacleDatePhoto spect : listeSpectacleDatePhoto) {
                            String str = ClasseHelp.ListeDatesToString(spect.getListeDate());
                    %>
                    <div class="spectacleMiniFiche">
                        <a href="/Mytheatre/Detail?nospectacle=<%=spect.getNumeroSpectacle()%>"><img class="photoSpectacle" src="<%String url = "Images/spectacle0.jpg";
                            if (spect.getPhotos().size() > 0) {
                                url = spect.getPhotos().get(0).toString();
                            };%><%=url%>" height="200" width="200" title="<%=str%>" onerror="imgError(this)"/></a><br>
                        <a class="titrePhoto" href="/Mytheatre/Detail?nospectacle=<%=spect.getNumeroSpectacle()%>"><%=spect.getNomSpectacle()%> </a>

                    </div>
                    <% }%>
                    <div class="clearMiniFiche" ></div>
                </div>
            </div>
            <div id="spacer"></div>
            <footer>
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $(document).ready(function () {

                $("#datePicker2").datepicker({
                    altField: "#datepicker",
                    closeText: 'Fermer',
                    prevText: 'Précédent',
                    firstDay: 1,
                    nextText: 'Suivant',
                    currentText: 'Aujourd\'hui',
                    monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
                    monthNamesShort: ['Janv.', 'Févr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Août', 'Sept.', 'Oct.', 'Nov.', 'Déc.'],
                    dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
                    dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
                    dayNamesMin: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
                    weekHeader: 'Sem.',
                    dateFormat: 'dd-mm-yy',
                    disabled: true,
                    minDate: new Date("<%=Min%>"),
                    maxDate: new Date("<%=Max%>"),
                    onSelect: function updateListeSpectacleDate() {
// on récupère dans une chaîne de caractères les paramètres du formulaire
// d'id choixDate. Si les dates sélectionnées sont 03-05-2017 et 10-05-2017, params aura la valeur suivante :
// "name=03-05-2017&name=10-05-2017
                        $("#datePicker2 , #datePicker1").datepicker("option", "dateFormat", 'yy-mm-dd');

                        var params = $("#choixDate").serialize();
// appel AJAX à la servlet d'URL listeDesSportifs avec les paramètres de
// requête params. Le code HTML retourné par cette servlet est inséré
// dans l'élément d'id listeSportifs

                        $("#spectaclesResultats").load("FiltrerSpectacle", params);

                    },
                    onClose: function () {
                        setTimeout(function () {
                            $("#datePicker2 , #datePicker1").datepicker("option", "dateFormat", 'dd-mm-yy');
                            $("#datePicker2").datepicker("option", "disabled", true);
                        }, 1);

                    }

                });
                $("#datePicker1").datepicker({
                    altField: "#datepicker",
                    closeText: 'Fermer',
                    prevText: 'Précédent',
                    firstDay: 1,
                    nextText: 'Suivant',
                    currentText: 'Aujourd\'hui',
                    monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
                    monthNamesShort: ['Janv.', 'Févr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Août', 'Sept.', 'Oct.', 'Nov.', 'Déc.'],
                    dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
                    dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
                    dayNamesMin: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
                    weekHeader: 'Sem.',
                    dateFormat: 'dd-mm-yy',
                    //dateFormat: 'yy-mm-dd',
                    minDate: new Date("<%=Min%>"),
                    maxDate: new Date("<%=Max%>"),
                    onSelect: function (date) {
                        $("#datePicker2").datepicker("option", "minDate", date);
                        $("#datePicker2").datepicker("option", "disabled", false);
                        $("#datePicker2").datepicker("setDate", date);
                        setTimeout(function () {
                            $("#datePicker2").datepicker('show');
                        }, 16);
                    }

                });

            <%if (!tousLesSpectaclesBool) {%>


                $('#datePicker1').datepicker("setDate", new Date("<%=MinAff%>"));
                $('#datePicker2').datepicker("setDate", new Date("<%=MaxAff%>"));
            <%}%>
            });
        </script>
    </body>
</html>
