var inputs = {};

window.onload = function () {
    var bouton = document.getElementById("btn");
    init();
    bouton.onclick = function () {
        sendRequete();
    };

    inputs["identifiant"].addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            bouton.click();
        }
    });

    inputs["pass"].addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            bouton.click();
        }
    })
};

function init() {
    inputs.identifiant = document.getElementById("identifiant");
    inputs.pass = document.getElementById("pass");
}

function sendRequete() {
    var requete = new XMLHttpRequest();
    requete.open("POST", "/ws/users/connexion", true);
    requete.onload = function () {
        if (this.status === 200) {
            window.location = "/private/Home";
        } else if (this.status === 201) {
            wrongInfos();
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    requete.send("identifiant="+inputs["identifiant"].value+
        "&pass="+inputs["pass"].value);

}

function wrongInfos() {
    inputs["identifiant"].classList.replace("txtbox","wrongTxtbox");
    inputs["pass"].classList.replace("txtbox","wrongTxtbox");
    inputs["pass"].value = "";
    document.getElementById("warnInfo").innerHTML = "Identifiant ou mot de passe incorrect !"
}