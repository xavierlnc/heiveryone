function loadFragments() {
    let searchBar = document.getElementById("rechercher");
    let searchButton = document.getElementById("searchButton");
    let searchList = document.getElementById("searchList");
    let navButton = document.getElementById("navButton");
    let navList = document.getElementById("navList");

    searchButton.onclick = function () {
        window.location = "/private/Search?search="+searchBar.value;
    };

    navButton.onclick = function () {
        if (navList.classList.contains("navDisplay")) {
            navList.classList.replace("navDisplay", "navHiden");
        } else {
            navList.classList.replace("navHiden", "navDisplay");
        }
    };

    document.getElementById("deconnexion").onclick = function () {
        var requete = new XMLHttpRequest();
        requete.open("POST", "/ws/users/deconnexion", true);
        requete.onload = function () {
            if (this.status === 200) {
                window.location = "/Connexion";
            } else {
                alert("Une erreur est survenue");
            }
        };
        requete.send();
    };

    searchBar.addEventListener("keyup", function () {
        if (event.keyCode === 13) {
            searchButton.click();
        } else if (searchBar.value !== "") {
            searchUser(searchBar.value);
        } else {
            searchList.innerHTML = "";
        }
    });

}

function searchUser(searchString) {
    var requete = new XMLHttpRequest();
    requete.open("GET", "/ws/users/searchUser/"+searchString, true);
    requete.onload = function () {
        constructListUser(this.response);
    };
    requete.responseType = "json";
    requete.send();
}

function constructListUser(users) {
    searchList.innerHTML = "";
    if (users === null || users.length === 0) {
        let lineElement = document.createElement("li");
        lineElement.classList.add("noResult");

        let searchLogo = document.createElement("img");
        searchLogo.src = "/logos/loupe.png";
        searchLogo.classList.add("noResultLogo");

        let infos = document.createElement("h2");
        infos.innerHTML = "Aucun résultat !";

        lineElement.appendChild(searchLogo);
        lineElement.appendChild(infos);
        searchList.appendChild(lineElement);
    } else {
        var limit = users.length;
        if (users.length>5) {
            limit = 5;
        }
        for (var i=0;i<limit;i++) {
            createSearchLineElement(users[i]);
        }
    }
}

function createSearchLineElement(user) {
    let lineElement = document.createElement("li");

    let lien = document.createElement("a");
    lien.href = "/private/account?id="+user["id"];

    let userImage = document.createElement("img");
    userImage.src = user["avatarPath"];

    let userName = document.createElement("h2");
    userName.innerHTML = user["name"]+" "+user["lastName"];

    lien.appendChild(userImage);
    lien.appendChild(userName);
    lineElement.appendChild(lien);
    searchList.appendChild(lineElement);
}