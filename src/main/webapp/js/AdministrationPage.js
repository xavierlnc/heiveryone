let inputs = {};

window.onload = function () {
    loadFragments();
    init();

    let delButtons = document.getElementsByClassName("delButton");
    for (let i=0; i<delButtons.length;i++) {
        delButtons[i].addEventListener("pointerup", function () {
            sendDelUserRequete(delButtons[i].id.replace("d",""));
        });
    }

    let saveButtons = document.getElementsByClassName("saveButton");
    for (let i=0; i<saveButtons.length;i++) {
        saveButtons[i].addEventListener("pointerup", function () {
            let id = saveButtons[i].id.replace("s","");
            let listStatus = document.getElementById("us"+id);
            let status = listStatus.options[listStatus.selectedIndex].text;
            sendSaveUserRequete(id,status);
        });
    }

    document.getElementById("addUserBtn").onclick = function () {
        sendAddUserRequete();
    }
};

function init() {
    inputs.nom = document.getElementById("lastName");
    inputs.prenom = document.getElementById("name");
    inputs.dob = document.getElementById("dob");
    inputs.mail = document.getElementById("mail");
    inputs.identifiant = document.getElementById("identifiant");
    inputs.pass = document.getElementById("pass");
}

function sendDelUserRequete(id) {
    if(confirm("Voulez-vous supprimer cet utilisateur : "+id+" ?")) {
        let requete = new XMLHttpRequest();
        requete.open("DELETE", "/ws/users/"+id, true);
        requete.onload = function () {
            if (this.status === 200) {
                alert("L'utilisateur a bien été supprimer !");
                location.reload();
            } else if (this.status === 204) {
                alert("Vous ne pouvez pas vous supprimer vous même !");
            } else if (this.status === 409) {
                alert("Une erreur SQL est survenue");
            } else {
                alert("Une erreur est survenue");
            }
        };
        requete.send();
    }
}

function sendSaveUserRequete(id,status) {
    if(confirm("Voulez-vous modifier le statut de cet utilisateur : "+id+" ?")) {
        let requete = new XMLHttpRequest();
        requete.open("PATCH", "/ws/users/updateUserStatus/"+id, true);
        requete.onload = function () {
            if (this.status === 200) {
                alert("Le statut de l'utilisateur a bien été modifié !");
                location.reload();
            } else {
                alert("Une erreur est survenue");
            }
        };
        requete.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        requete.send("status="+status);
    }
}

function sendAddUserRequete(name,lastName,dob,mail,identifiant,pass) {
    let requete = new XMLHttpRequest();
    requete.open("POST", "/ws/users", true);
    requete.onload = function () {
        if (this.status === 200) {
            alert("L'utilisateur a bien été ajouté !");
            location.reload();
        } else if (this.status === 204) {
            inputsEmpty();
            idAlreadyUsed();
        } else if (this.status === 205) {
            inputsEmpty();
        } else if (this.status === 409) {
            alert("Une erreur SQL est survenue");
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    requete.send("lastName="+inputs["nom"].value+
        "&name="+inputs["prenom"].value+
        "&dob="+inputs["dob"].value+
        "&mail="+inputs["mail"].value+
        "&identifiant="+inputs["identifiant"].value+
        "&pass="+inputs["pass"].value+
        "&inscriptionPerso="+false
    );

}

function idAlreadyUsed() {
    inputs["identifiant"].value = "";
    inputs["identifiant"].placeholder = "Id utilisée !";
    inputs["identifiant"].classList.replace("txtbox","wrongTxtbox");
}

function inputsEmpty() {
    for (input in inputs) {
        var content = inputs[input].value;
        if (content === "") {
            inputs[input].classList.replace("txtbox", "wrongTxtbox");
        } else {
            inputs[input].classList.replace("wrongTxtbox", "txtbox");
        }
    }
}