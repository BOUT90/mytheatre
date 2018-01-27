<%-- 
    Document   : achatPlaces
    Created on : 2 mars 2017, 20:25:34
    Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>

<%@page import="Modeles.UtilisateurInfos"%>
<%@page import="Modeles.SpectacleDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSC Demo</title>
        <meta charset="UTF-8">
        <link href="Styles/StylesMyTheatre.css" rel="stylesheet" type="text/css"/>
        <link href="scripts/jQuery-Seat-Charts/jquery.seat-charts.css" rel="stylesheet" type="text/css"/>
        <link href="Styles/StyleTheatreAchatPlace.css" rel="stylesheet" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="scripts/Visibilite.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="wrapper2">
            <header>
                <!-- la partie login : logo + login -->
                <% UtilisateurInfos user = (UtilisateurInfos) request.getSession().getAttribute("user");
                    if (user != null) {%>
                <div id="logoCompteUtilisateur"> <a href="/Mytheatre/ConnexionValidation?connexionPseudo=<%=user.getPseudo()%>&connexionMotDePasse=<%=user.getPassword()%>" title="Mon Compte"><img src="Images/user.png" width="20px"/><span>  <%=user.getPseudo()%></span></a></div>
                        <%}%>
                <nav>
                    <ul>
                        <li><a href="/Mytheatre/indexCtrler">Accueil</a></li>
                        <li><a href="/Mytheatre/Spectacle?typeCommade=general">Spectacle</a></li>
                        <li id="connexion"><%if (user != null) {%><a href="/Mytheatre/Deconnexion" >Deconnexion</a><%}%><%if (user == null) {%><a href="/Mytheatre/Connexion" >Connexion</a><%}%></li>
                    </ul>
                </nav>    
            </header>

            <div class="detailPage">
                <div class="wrapper1">
                    <h1 id="representationDate">
                        Représentation : <% SpectacleDate sp = (SpectacleDate) request.getSession().getAttribute("spectacle");%>
                        <%= sp.getNomSpectacle()%>
                        <% String date = request.getParameter("date");%>
                        <span id ="dateSpectacle" value="<%= date%>"><%= date%></span>

                    </h1>
                    <p style="color: green; font-size: 140%;">Nombre de places disponibles en ligne:<span id="Nombrepalcedispo"></span>
                    <div id="map-container">
                        <div id="seat-map">
                            <div class="front-indicator">Scène</div>
                        </div>
                        <div id="commande">
                            <div id="legend"></div>
                            <!-- Les linkerZones permettent de rediriger vers des zones spécifiques.. mais la JSP ne marche pas avec cette technique--> 
                            <a href="#1_1"><div id="linkerZone1"></div></a>
                            <a href="#20_1"><div id="linkerZone2"></div></a>
                            <a href="#30_1"><div id="linkerZone3"></div></a>
                            <a href="#40_1"><div id="linkerZone4"></div></a>
                            <div id="payer">
                                <h3>Votre sélection</h3>

                                <div id="tousLesPrixEtNbPlace">
                                    <p>Nombre de places:</br><strong><span id="nbplaces">0</span></strong></p>
                                    <div id="totalPrix"><p style="color:white">SPACER ------</br></br></p><p>Prix Total: </br><strong><span id="prixtotal">0</span> €</strong></p></div>
                                    <div id="reducEtTotal" hidden><p>Remise: </br><strong><span id="remise">0</span> €</strong></p>
                                        <p>Prix total avec remise: </br><strong><span id="totalRemise">0</span> €</strong></p>
                                    </div>
                                </div><input type='submit' id="achatBtn" value='Payer' disabled/>
                                <div style="height:72px;"><p id="messageAlert" style="color:red;"> </p></div>

                                <%String numSpecStr = (String) request.getSession().getAttribute("nomSpectacleStr");%>

                            </div>

                           
                            <div class="centerStyle">
                                 <img src="Images/promotion.jpg" alt="" title="-25% à partir de 10 places achetées!" width="100px"/><br>
                                 <marquee scrollamount="8" bgcolor="lightGray" width="165px" style="font-size: 120%">*** Profitez de 25% de réduction à partir de 10 places achetées ! *** </marquee>
                                 </br>
                                 </br>
                                 </br>
                                 </br>
                                  <a href="/Mytheatre/Detail?nospectacle=<%=numSpecStr%>&date=<%=date%>" style="font-size: 130%">Choisir Autre Date</a><br>
                            </div>
                            <div class="retourBouton">
                              
                            </div>
                        </div>


                    </div>

                </div>
            </div>
            <div id="spacer"> <a href="#">Haut de Page</a></div>
            <footer id="footerMap">
                <p>MyTheatre ©&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel : 04 57 42 21 42&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;email : contact@mytheatre.com</p>
            </footer>
        </div>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="scripts/jQuery-Seat-Charts/jquery.seat-charts.min.js" type="text/javascript"></script>
        <script>
            var firstSeatLabel = 1;
            $(document).ready(function () {
                var $detailCategorie = $('#detail-categories'),
                        $nbPlaces = $('#nbplaces'),
                        $prixTotal = $('#prixtotal'),
                        $remise = $('#remise'),
                        $totalRemise = $('#totalRemise'),
                        $Nombrepalcedispo = $('#Nombrepalcedispo'),
                        sc = $('#seat-map').seatCharts({
                    map: [
                        'AAAAA__AAAAAAAAAA__AAAAA',
                        'AAAAA__AAAAAAAAAA__AAAAA',
                        'AAAAA__AAAAAAAAAA__AAAAA',
                        'AAAAA__AAAAAAAAAA__AAAAA',
                        'AAAAA__AAAAAAAAAA__AAAAA',
                        'BBBBB__BBBBBBBBBB__BBBBB',
                        'BBBBB__BBBBBBBBBB__BBBBB',
                        'BBBBB__BBBBBBBBBB__BBBBB',
                        'BBBBB__BBBBBBBBBB__BBBBB',
                        'BBBBB__BBBBBBBBBB__BBBBB',
                        'CCCCC__CCCCCCCCCC__CCCCC',
                        'CCCCC__CCCCCCCCCC__CCCCC',
                        'CCCCC__CCCCCCCCCC__CCCCC',
                        'CCCCC__CCCCCCCCCC__CCCCC',
                        'CCCCC__CCCCCCCCCC__CCCCC',
                        'DDDDD__DDDDDDDDDD__DDDDD',
                        'DDDDD__DDDDDDDDDD__DDDDD',
                        'DDDDD__DDDDDDDDDD__DDDDD',
                        'DDDDD__DDDDDDDDDD__DDDDD',
                        'DDDDD__DDDDDDDDDD__DDDDD'
                    ],
                    seats: {
                        A: {
                            price: 50,
                            classes: 'categorieA', // votre classe CSS spécifique
                            category: 'A'
                        },
                        B: {
                            price: 40,
                            classes: 'categorieB', // votre classe CSS spécifique
                            category: 'B'
                        },
                        C: {
                            price: 20,
                            classes: 'categorieC', // votre classe CSS spécifique
                            category: 'C'
                        },
                        D: {
                            price: 10,
                            classes: 'categorieD', // votre classe CSS spécifique
                            category: 'D'
                        }

                    },
                    naming: {
                        top: false,
                        getLabel: function (character, row, column) {
                            return ((firstSeatLabel++ - 1) % 20) + 1;
                        },
                    },
                    legend: {
                        node: $('#legend'),
                        items: [
                            ['A', 'available', 'Orchestre 50€'],
                            ['B', 'available', '1er Balcon 40€'],
                            ['C', 'available', '2nd Balcon 20€'],
                            ['D', 'available', 'Poulailler 10€'],
                            [, 'unavailable', 'Place non disponible']
                        ]
                    },
                    click: function () {
                        $("#messageAlert").prop('hidden', true);
                        var messageAlert = document.getElementById("messageAlert");



                        if (this.status() === 'available') {

                            if ((400 - sc.find('unavailable').length - (sc.find('selected').length)) <= 70) {
                                //alert("Tu peux plus acheter de places\nLes 70 places restantes seront vendues au guichet");
                                //  $("#achatBtn").prop('disabled', true);
                                $("#messageAlert").prop('hidden', false);
                                messageAlert.innerHTML = "Il ne reste que " + ((400 - sc.find('unavailable').length - 70)) + " place(s) disponible(s) en ligne, veuillez vous adreser au guichet.";
                                //afficherReducOuNon(sc);
                                return 'available';

                            }
                            else {
                                //messageAlert.innerHTML = "";
                                /*
                                 * Une place disponible a été sélectionnée
                                 * Mise à jour du nombre de places et du prix total
                                 *
                                 * Attention la fonction .find  ne prend pas en compte 
                                 * la place qui vient d'être selectionnée, car la liste des
                                 * places sléectionnées ne sera modifiée qu'après le retour de cette fonction.
                                 * C'est pourquoi il est nécessaire d'ajouter 1 au nombre de places et le prix
                                 * de la place sélectionnée au prix calculé.
                                 */
                                $nbPlaces.text(sc.find('selected').length + 1);
                                $prixTotal.text(calculerPrixTotal(sc) + this.data().price);
                                var total = calculerPrixTotal(sc, 1) + this.data().price;
                                if (sc.find('selected').length + 1 > 9) {

                                    var remise = total * 25 / 100;
                                    var totalRemise = total - remise;
                                } else {
                                    var remise = 0;
                                    var totalRemise = 0;
                                }
                                $remise.text(remise);
                                $totalRemise.text(totalRemise);

                                if (sc.find('selected').length + 1 > 0) {
                                    $("#achatBtn").prop('disabled', false);
                                }
                                afficherReducOuNon(sc, 1);
                                return 'selected';
                            }
                        }
                        else if (this.status() == 'selected') {
                            $nbPlaces.text(sc.find('selected').length - 1);
                            $prixTotal.text(calculerPrixTotal(sc) - this.data().price);
                            var total = calculerPrixTotal(sc) - this.data().price;
                            if (sc.find('selected').length - 1 > 9) {

                                var remise = total * 25 / 100;
                                var totalRemise = total - remise;
                            } else {
                                var remise = 0;
                                var totalRemise = 0;
                            }
                            $remise.text(remise);
                            $totalRemise.text(totalRemise);

                            if (sc.find('selected').length - 1 == 0) {
                                $("#achatBtn").prop('disabled', true);
                            }
                            // la place est désélectionnée
                            afficherReducOuNon(sc, -1);
                            return 'available';
                        } else if (this.status() == 'unavailable') {
                            // la place a déjà été achetée.
                            afficherReducOuNon(sc);
                            return 'unavailable';
                        } else {
                            afficherReducOuNon(sc);
                            return this.style();
                        }
                    }

                });

                majPlanSalle();
                setInterval(majPlanSalle, 10000); //every 10 seconds

                $("#achatBtn").click(function () {
                    acheter(sc);
                });

                function majPlanSalle() {
                    $.ajax({
                        type: 'get',
                        url: 'placesNonDisponibles',
                        dataType: 'json',
                        success: function (reponse) {
                            // iteration sur toutes les réservations de reponse
                            $.each(reponse.bookings, function (index, reservation) {
                                //mettre le status du siège correspondant à non disponible
                                sc.status(reservation.rang + '_' + reservation.colonne, 'unavailable');
                                $Nombrepalcedispo.text(400 - sc.find('unavailable').length - 70);
                            });
                            $nbPlaces.text(sc.find('selected').length);
                            $prixTotal.text(calculerPrixTotal(sc));
                            var total = calculerPrixTotal(sc);
                            if (sc.find('selected').length > 9) {

                                var remise = total * 25 / 100;
                                var totalRemise = total - remise;
                            } else {
                                var remise = 0;
                                var totalRemise = 0;
                            }
                            $remise.text(remise);
                            $totalRemise.text(totalRemise);
                            if (sc.find('selected').length == 0 || (400 - sc.find('unavailable').length) <= 70) {
                                $("#achatBtn").prop('disabled', true);
                                if ((400 - sc.find('unavailable').length) <= 290) {
                                    //alert("Tu peux plus acheter en ligne");
                                }
                            } else {
                                $("#achatBtn").prop('disabled', false);
                            }
                        }
                    });

                }

            });

            function calculerPrixTotal(sc) {
                var total = 0;
                //retrouver toutes les places sélectionnées et sommer leur prix
                sc.find('selected').each(function () {
                    total += this.data().price;
                });
                return total;
            }

            function acheter(sc) {
                var params = "";
                var premier = true;
                sc.find('selected').each(function () {
                    if (premier) {
                        params = params + "place=";
                        premier = false;
                    } else {
                        params = params + "&place=";
                    }
                    // on calcule l'id de la place en fonction de l'id du div dans lequel elle se trouve, plus à partir du label comme originalement.
                    var idDiv = this.settings.id;
                    var pieces = idDiv.split("_");
                    var rang = parseInt(pieces[0], 10);
                    var colonne = parseInt(pieces[1], 10);
                    var idPlace = 0;
                    if (colonne <= 5) {
                        idPlace = colonne + ((rang - 1) * 20); // + 1 - 1 pour parser
                    } else if (colonne >= 8 && colonne <= 17) {
                        idPlace = colonne - 2 + ((rang - 1) * 20);
                    } else {
                        idPlace = colonne - 4 + ((rang - 1) * 20);
                    }

                    params = params + idPlace;
                });
                //var dateRecup = document.getElementById("dateSpectacle").value;
                //alert(dateRecup);
                location.replace("acheterPlaces?" + params + "&date=" + "<%= request.getParameter("date")%>");
            }

            function afficherReducOuNon(sc, i) {
                if (sc.find('selected').length + i >= 10) {
                    $("#totalPrix").prop('hidden', true);
                    $("#reducEtTotal").prop('hidden', false);
                } else {
                    $("#totalPrix").prop('hidden', false);
                    $("#reducEtTotal").prop('hidden', true);
                }
            }
        </script>
    </body>
</html>
