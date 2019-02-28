var inputs = {};

window.onload = function () {
    var bouton = document.getElementById("btn");
    init();
    bouton.onclick = function () {
        sendRequete();
    }
};

function init() {
    inputs.nom = document.getElementById("nom");
    inputs.prenom = document.getElementById("prenom");
    inputs.dob = document.getElementById("date_de_naissance");
    inputs.mail = document.getElementById("mail");
    inputs.identifiant = document.getElementById("identifiant");
    inputs.password = document.getElementById("password");
}

function sendRequete() {
    var requete = new XMLHttpRequest();
    requete.open("POST", "/ws/users/", true);
    requete.onload = function () {
        if (this.status === 200) {
            window.location = "/private/Home";
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
    requete.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    requete.send("lastName="+inputs["nom"].value+
        "&name="+inputs["prenom"].value+
        "&dob="+inputs["dob"].value+
        "&mail="+inputs["mail"].value+
        "&identifiant="+inputs["identifiant"].value+
        "&pass="+inputs["password"].value+
        "&inscriptionPerso="+true);

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
