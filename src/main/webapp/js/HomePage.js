window.onload = function () {
    loadFragments();
    
    let button = document.getElementById("btn");
    button.onclick = function () {
        sendAddPostRequete();
    };

    let textarea = document.getElementById("newPostText");
    textarea.addEventListener("click", function (ev) {
        if (textarea.classList.contains("txtBoxWrong")) {
            textarea.removeAttribute("class","txtBoxWrong");
            textarea.placeholder = "Ecrivez quelque chose..."
        }
    });

    let delPostButton = document.getElementsByClassName("delPostButton");
    for (let i=0;i<delPostButton.length;i++) {
        delPostButton[i].addEventListener("pointerup", function () {
            let id = delPostButton[i].id.replace("sp","");
            sendDelPostRequete(id);
        })
    }

    let likePostButton = document.getElementsByClassName("likeBtn");
    for (let i=0;i<likePostButton.length;i++) {
        likePostButton[i].addEventListener("pointerup", function () {
            let id = likePostButton[i].id.replace("lb","");
            sendLikeRequete(id);
        })
    }

    let dislikePostButton = document.getElementsByClassName("dislikeBtn");
    for (let i=0;i<dislikePostButton.length;i++) {
        dislikePostButton[i].addEventListener("pointerup", function () {
            let id = dislikePostButton[i].id.replace("db","");
            sendDislikeRequete(id);
        })
    }
};

function sendAddPostRequete() {
    let requete = new XMLHttpRequest();
    let textarea = document.getElementById("newPostText");
    requete.open("POST", "/ws/post", true);
    requete.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    requete.onload = function () {
        if (this.status === 200) {
            window.location = "/private/Home";
        } else if (this.status === 204) {
            emptyPost();
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.send("newPostText="+textarea.value);
}

function emptyPost() {
    let textarea = document.getElementById("newPostText");
    textarea.setAttribute("class", "txtBoxWrong");
    textarea.placeholder = "Le post est vide !";
}

function sendDelPostRequete(id) {
    let requete = new XMLHttpRequest();
    requete.open("DELETE", "/ws/post/"+id, true);
    requete.onload = function () {
        if (this.status === 200) {
            location.reload();
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.send();
}

function sendLikeRequete(postId) {
    let requete = new XMLHttpRequest();
    requete.open("PATCH", "/ws/post/like/"+postId, true);
    requete.onload = function () {
        if (this.status === 200) {
            location.reload();
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.send();
}

function sendDislikeRequete(postId) {
    let requete = new XMLHttpRequest();
    requete.open("PATCH", "/ws/post/dislike/"+postId, true);
    requete.onload = function () {
        if (this.status === 200) {
            location.reload();
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.send();
}
