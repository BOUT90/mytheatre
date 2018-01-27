/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function connexionPossible() {
    if (document.getElementById("connexionInput1").value !== "" && document.getElementById("connexionInput2").value !== "") {
        document.getElementById("connexionInput3").disabled = false;
    } else {
        document.getElementById("connexionInput3").disabled = true;
    }
}

function validatePasswd() {
    var nouvmotdepasse = document.getElementById("changementmotdepasse1").value;
    var confirmmotdepasse = document.getElementById("changementmotdepasse2").value;
    var passwdMessDiv = document.getElementById("modepassfaux");
    if ((nouvmotdepasse === "") && (confirmmotdepasse === "")) {
        passwdMessDiv.innerHTML = "<div style=\"color:red\">Veuillez entrer vos mots de passe</div>";
        disableSubmitBtn(true);
    } else if (nouvmotdepasse === confirmmotdepasse) {
        passwdMessDiv.innerHTML = "";
        disableSubmitBtn(false);
    }
    else {
        passwdMessDiv.innerHTML = "<div style=\"color:red\">Les deux mots de passe ne sont pas identiques</div>";
        disableSubmitBtn(true);
    }
}

function disableSubmitBtn(val) {
    var submitBtn = document.getElementById("changementmotdepasse3");
    submitBtn.disabled = val;
}
function validatePasswdCreationCompte() {
    var nouvmotdepasse = document.getElementById("motdepassecreationcompte").value;
    var confirmmotdepasse = document.getElementById("confmotdepassecreationcompte").value;
    var passwdMessDiv = document.getElementById("passwordpasidentique");
    if ((nouvmotdepasse === "") && (confirmmotdepasse === "")) {
        passwdMessDiv.innerHTML = "<div style=\"color:red\">Veuillez entrer vos mots de passe</div>";
        disableSubmitBtnvaliCompte(true);
    } else if (nouvmotdepasse === confirmmotdepasse) {
        passwdMessDiv.innerHTML = "";
        disableSubmitBtnvaliCompte(false);
    }
    else {
        passwdMessDiv.innerHTML = "<div style=\"color:red\">Les deux mots de passe ne sont pas identiques</div>";
        disableSubmitBtnvaliCompte(true);
    }
}
function disableSubmitBtnvaliCompte(val) {
    var submitBtn = document.getElementById("validationcreationcompte");
    submitBtn.disabled = val;
}

function retourGerant() {

    document.getElementById("prod").disabled = true;
    document.getElementById("gerant").disabled = false;


}

function retourProd() {

    document.getElementById("gerant").disabled = true;
    document.getElementById("prod").disabled = false;
    
}

function changementInfos() {
    if (document.getElementById("changementinfo1").value !== "" && document.getElementById("changementinfo2").value !== "" && document.getElementById("changementinfo3").value !== ""
            && document.getElementById("changementinfo4").value !== "") {
        document.getElementById("changementinfo9").disabled = false;
    } else {
        document.getElementById("changementinfo9").disabled = true;
    }
}

function changementMotDepass() {
    if (document.getElementById("changementmotdepasse1").value !== "" && document.getElementById("changementmotdepasse2").value !== "") {
        document.getElementById("changementmotdepasse3").disabled = false;
    } else {
        document.getElementById("changementmotdepasse3").disabled = true;
    }
}
function creationCompte() {

    if (document.getElementById("pseudocreationcompte").value !== "" && document.getElementById("nomcreationcompte").value !== ""
            && document.getElementById("prenomcreationcompte").value !== "" && document.getElementById("adresseemailcreationcompte").value !== ""
            && document.getElementById("motdepassecreationcompte").value !== "" && document.getElementById("confmotdepassecreationcompte").value !== "") {
        document.getElementById("validation").disabled = false;
    } else {
        document.getElementById("validation").disabled = true;
    }
}
function ajoutPossible(x) {

    var toutesLesDates = true;
    var toutesLesHeures = true;
    var checkbox;

    $('.ajoutDate').each(function () {
        toutesLesDates = toutesLesDates && $(this).val() !== "";
    });
    $('.ajoutTime').each(function () {
        toutesLesHeures = toutesLesHeures && $(this).val() !== "";
    });
    checkbox = $('#checkboxRequired').prop('checked');

    if ($('#ajoutTitre').val() !== "" &&
            $('#ajoutDuree').val() !== "" &&
            toutesLesDates &&
            toutesLesHeures &&
            checkbox) {
        undisable(x);
    } else {
        disable(x);
    }
}

function ajouterDateSup() {

    $(".DateDeRepresentation").last().clone().appendTo("#ajoutDeDateSup");
    $("#supprimerDate").prop('disabled', false);
    var nbDate = parseInt($("#nbDateTot").val(), 10) + 1;
    $('#nbDateTot').attr('value', nbDate);
    $('.DateDeRepresentation .ajoutDate').last().attr('name', "date" + $('.DateDeRepresentation').length);
    $('.DateDeRepresentation .ajoutTime').last().attr('name', "time" + $('.DateDeRepresentation').length);
    var numDate = parseInt($(".dateNumber").last().text());
    $(".dateNumber").last().text(numDate + 1);
    $("#toutesLesDates").animate({scrollTop: $("#toutesLesDates")[0].scrollHeight}, 1000);
}

function supprimerUneDate() {
    var size = $('.DateDeRepresentation').length;
    if (size > 2) {
        $('.DateDeRepresentation').last().remove();
    } else {
        $('.DateDeRepresentation').last().remove();
        $("#supprimerDate").prop('disabled', true);
    }
    var nbDate = parseInt($("#nbDateTot").val(), 10) - 1;
    $('#nbDateTot').attr('value', nbDate);
    ajoutPossible("ajoutValider");
}

function ajouterPhotoSup() {

    $(".ulrPhotoEtApercu").last().clone().appendTo("#ajoutDePhotoSup");
    $("#supprimerPhoto").prop('disabled', false);

    var nbPhoto = parseInt($("#nbPhotoTot").val(), 10) + 1;
    $('#nbPhotoTot').attr('value', nbPhoto);
    $('.ulrPhotoEtApercu .urlPhoto').last().attr('name', "urlPhoto" + $('.ulrPhotoEtApercu').length);
    $('.ulrPhotoEtApercu .urlPhoto').last().attr('id', "urlPhoto" + $('.ulrPhotoEtApercu').length);
    $('.ulrPhotoEtApercu .photoApercu').last().attr('src', "");
    $('.ulrPhotoEtApercu .urlPhoto').last().val("");
    $('.photoApercu').last().attr('id', 'photoApercu' + $('.ulrPhotoEtApercu').length);
    $('.urlPhoto').last().attr('oninput', 'apercuMiniPhoto(' + $('.ulrPhotoEtApercu').length + ')');
    var numDate = parseInt($(".photoNumber").last().text());
    $(".photoNumber").last().text(numDate + 1);
    $("#toutesLesPhotos").animate({scrollTop: $("#toutesLesPhotos")[0].scrollHeight}, 1000);
}

function supprimerUnePhoto() {
    var size = $('.ulrPhotoEtApercu').length;
    if (size > 2) {
        $('.ulrPhotoEtApercu').last().remove();
    } else {
        $('.ulrPhotoEtApercu').last().remove();
        $("#supprimerPhoto").prop('disabled', true);
    }
    var nbDate = parseInt($("#nbPhotoTot").val(), 10) - 1;
    $('#nbPhotoTot').attr('value', nbDate);
    // ajoutPossible(); plus besoin de cette fonction avec l'utilisation du paramètre required.
}

function apercuMiniPhoto(x) {
    $("#photoApercu" + x).attr('src', $("#urlPhoto" + x).val());
    if ($("#urlPhoto" + x).val() === "") {
        $("#photoApercu" + x).attr('src', "Images/spectacle0.jpg");
    }
}

function imgError(image) {
    image.src = "Images/spectacle0.jpg";
    return true;
}

function undisable(id) {
    $('#' + id).attr('class', 'undisabled');
}

function disable(id) {
    $('#' + id).attr('class', 'disabled');
}

$(document).ready(function () {

    $("#envoiMailDetail").submit(function () {

        var params = $("#envoiMailDetail").serialize();

        $("#reponseMail").load("sendInfoSpec", params);
        return false;
    });
    
        $("#envoiMailAchat").submit(function () {

        var params = $("#envoiMailAchat").serialize();

        $("#reponseMail").load("sendTicket", params);
        return false;
    });

}
);