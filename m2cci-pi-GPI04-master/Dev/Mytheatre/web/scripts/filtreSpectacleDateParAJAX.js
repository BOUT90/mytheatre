
$(function () {
    // cette fonction est executée un fois que le navigateur a terminé le chargement
    // de la page et la construction du DOM)


    // on associe au formulaire d'id choixDate un gestionnaire d'événements
    // qui dès que l'un des inputs de ce formulaire (c'est à dire l'un des deux champs
    // input date) est modifié, invoque la fonction updateListeSpectacleDate
    $("#date1").change(function () {         
        updateListeSpectacleDate();
        MAJ_Date();

         
    });

    $("#date2").change(function () {
        updateListeSpectacleDate();
       
    });

});

function updateListeSpectacleDate() {
    // on récupère dans une chaîne de caractères les paramètres du formulaire
    // d'id choixDate. Si les dates sélectionnées sont 03-05-2017 et 10-05-2017, params aura la valeur suivante :
    // "name=03-05-2017&name=10-05-2017
    var params = $("#choixDate").serialize();
    // appel AJAX à la servlet d'URL listeDesSportifs avec les paramètres de
    // requête params. Le code HTML retourné par cette servlet est inséré
    // dans l'élément d'id listeSportifs
    $("#choix").load("Spectacle", params);

}

function MAJ_Date()
{
    //var date = document.getElementById("date1").value;
    document.getElementById("date2").value = document.getElementById("date1").value;
}

