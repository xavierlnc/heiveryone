window.onload = function () {
    loadFragments();

    let stalkBtn = document.getElementsByClassName("stalkBtn")[0];
    if (stalkBtn != null || stalkBtn !== undefined) {
        stalkBtn.addEventListener("pointerup" ,function () {
            let id = stalkBtn.id.replace("stalk","");
            sendStalkRequete(id);
        });
    }


    let unStalkBtn = document.getElementsByClassName("unstalkBtn")[0];
    if(unStalkBtn != null || unStalkBtn !== undefined) {
        unStalkBtn.addEventListener("pointerup" ,function () {
            let id = unStalkBtn.id.replace("unstalk","");
            sendUnstalkRequete(id);
        });
    }

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

function sendStalkRequete(id) {
    var requete = new XMLHttpRequest();
    requete.open("POST", "/ws/users/stalk", true);
    requete.onload = function () {
        if (this.status === 200) {
            window.location = "/private/account?id="+id;
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    requete.send("target_userId="+id);
}

function sendUnstalkRequete(id) {
    var requete = new XMLHttpRequest();
    requete.open("DELETE", "/ws/users/stalk", true);
    requete.onload = function () {
        if (this.status === 200) {
            window.location = "/private/account?id="+id;
        } else {
            alert("Une erreur est survenue");
        }
    };
    requete.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    requete.send("target_userId="+id);

}

function sendDelPostRequete(id) {
    let requete = new XMLHttpRequest();
    requete.open("DELETE", "/ws/post/"+id, true);
    requete.onload = function () {
        console.log(this.status);
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