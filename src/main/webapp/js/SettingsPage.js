window.onload = function () {
    loadFragments();

    document.getElementById("save").onclick = function () {
        sendRequete();
    };

};

function handleFiles(files) {
    let file = files[0];
    let img = document.getElementById("avatarPicture");
    img.file = file;
    let reader = new FileReader();
    reader.onload = (function(aImg) { return function(e) { aImg.src = e.target.result; }; })(img);
    reader.readAsDataURL(file);
}



function sendRequete() {

    //Params
    let name = document.getElementById("name").value;
    let lastName = document.getElementById("lastName").value;
    let dob = document.getElementById("dob").value;
    let description = document.getElementById("description").value;
    let theme = document.getElementById("theme").value;

    /*
    var reader = new FileReader();
    var avatarFile;
    reader.addEventListener('load', function() {
        avatarFile = reader.result;
    });
    reader.readAsText(document.getElementById("avatar").files[0]);
    console.log(avatarFile);
    */

    let mail = document.getElementById("mail").value;
    let oldPass = document.getElementById("oldPass").value;
    let newPass = document.getElementById("newPass").value;
    let confNewPass = document.getElementById("confNewPass").value;

    //Requete
    let requete = new XMLHttpRequest();
    requete.open("PATCH", "/ws/users/updateUser", true);
    requete.onload = function () {
        if (this.status === 200) {
            location.reload();
        } else if (this.status === 409) {
            alert("Une erreur SQL est survenue");
        }
    };
    requete.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    requete.send("name="+name+
        "&lastName="+lastName+
        "&dob="+dob+
        "&description="+description+
        "&theme="+theme+/*
        "&avatar="+avatarFile+*/
        "&mail="+mail+
        "&oldPass="+oldPass+
        "&newPass="+newPass+
        "&confNewPass="+confNewPass
    );
}