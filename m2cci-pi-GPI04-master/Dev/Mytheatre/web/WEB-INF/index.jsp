<%-- 
    Document   : index
    Created on : 14 mars 2017, 14:18:13
    Author     : lagierg
--%>

<%@page import="Modeles.UtilisateurInfos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modeles.SpectacleDatePhoto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Bienvenu sur Mytheatre</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="Styles/StylesMyTheatre.css" rel="stylesheet" type="text/css"/>
        <link href="Styles/StyleSlider.css" rel="stylesheet" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
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
                        <li><a href="#" class="encours">Accueil</a></li>
                        <li><a href="/Mytheatre/Spectacle?typeCommade=general">Spectacle</a></li>
                        <li id="connexion"><%if (user != null) {%><a href="/Mytheatre/Deconnexion" >Deconnexion</a><%}%><%if (user == null) {%><a href="/Mytheatre/Connexion" >Connexion</a><%}%></li>
                    </ul>
                </nav>
            </header>
            <div class="detailPage">
                
                <!-- le contenu de la page -->
                <section class="presentation">
                    
                    <div class="centerStyle">
                        <h1>Bienvenue sur le site MyTheatre</h1> 
                    </div>
                    <!-- La partie suivante est le slider-->
                    <div id="slider">
                        <a href="#" class="control_next">></a>
                        <a href="#" class="control_prev"><</a>
                        <ul>
                            <% List<SpectacleDatePhoto> spectacles = (List<SpectacleDatePhoto>) request.getAttribute("SpectaclesPourAffiche");
                                for (SpectacleDatePhoto spec : spectacles) {
                                    if (spec.getPhotos().size() > 0) { // certains pectacle peuvent ne pas avoir de photo d'affiche.
                            %>
                            <li><a  href ="/Mytheatre/Detail?nospectacle=<%=spec.getNumeroSpectacle()%>">       <img class="photoSpectacle" src="<%=spec.getPhotos().get(0)%>" width="300px" height="300px" onerror="imgError(this)" title="<%=spec.getNomSpectacle()%>"/></li></a>

                            <%}
                                }%>

                        </ul>  
                    </div>
                    <!-- Le slider se termine ici-->
                    </br>
                    </br>
                    </br>
                    </br>
                    </br>


                    <!-- Partie tarif -->
                    <!--<div class="centerStyle">
                        <h3>Tarifs des places :</h3>
                        <ul>
                            <li>Orchestre 50€</li>
                            <li>1er Balcon 40€</li>
                            <li>2nd Balcon 20€</li>
                            <li>Poulailler 10€</li>
                        </ul>
                    </div>-->
                    <!-- partie promotion -->
                    <div id="promotionAccueil">
                        <img src="Images/promotion.jpg" alt="" title="-25% à partir de 10 places achetées!" width="150px"/>
                    <marquee scrollamount="15" bgcolor="lightGray">*** Profitez de 25% de réduction à partir de 10 places achetées ! *** </marquee>
                    </div>
                </section>
            </div>
                                <div id="spacer"></div>
            <footer>
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>
        <script>
            jQuery(document).ready(function () {


                setInterval(function () {
                    moveRight();
                }, 4000);


                var slideCount = $('#slider ul li').length;
                var slideWidth = $('#slider ul li').width();
                var slideHeight = $('#slider ul li').height();
                var sliderUlWidth = slideCount * slideWidth;

                $('#slider').css({width: slideWidth, height: slideHeight});

                $('#slider ul').css({width: sliderUlWidth, marginLeft: -slideWidth});

                $('#slider ul li:last-child').prependTo('#slider ul');

                function moveLeft() {
                    $('#slider ul').animate({
                        left: +slideWidth
                    }, 200, function () {
                        $('#slider ul li:last-child').prependTo('#slider ul');
                        $('#slider ul').css('left', '');
                    });
                }
                ;

                function moveRight() {
                    $('#slider ul').animate({
                        left: -slideWidth
                    }, 200, function () {
                        $('#slider ul li:first-child').appendTo('#slider ul');
                        $('#slider ul').css('left', '');
                    });
                }
                ;

                $('a.control_prev').click(function () {
                    moveLeft();
                });

                $('a.control_next').click(function () {
                    moveRight();
                });

            });


        </script>
    </body>
</html>

