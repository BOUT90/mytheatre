/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function compareMotDePasse() {

    var nouvmotDepass = document.getElementById("changementmotdepasse1");
    var confirmotDepass = document.getElementById("changementmotdepasse2");

    if (nouvmotDepass != confirmotDepass) { document.getElementById("changementmotdepasse3").disabled = true;
         $("#motdepassfaux").val("Mot de passae faux");
    } else {
        
       


    }


}

