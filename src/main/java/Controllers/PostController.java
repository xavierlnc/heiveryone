package Controllers;


import Exceptions.IdentifiantAlreadyExistsException;
import Exceptions.InputsEmptyException;
import entites.Post;
import entites.User;
import manager.PostLibrary;
import manager.UserLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

@Path("/post")
public class PostController {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newPost(MultivaluedMap<String, String> params , @Context HttpServletRequest request) {

        //Get params
        String texte = params.get("newPostText").get(0);

        LocalDate dateOfPost = LocalDate.now();
        Time timeOfPost = new Time(System.currentTimeMillis());
        String current_userIdentifiant = (String) request.getSession().getAttribute("utilisateurConnecte");
        //Add post
        try {
            User user = UserLibrary.getInstance().getUser(current_userIdentifiant);
            PostLibrary.getInstance().addPostOfUser(user, dateOfPost, timeOfPost, texte);
            return Response.status(200).build();
        } catch (SQLException e) {
            return Response.status(409).build();
        } catch (InputsEmptyException e) {
            return Response.status(204).build();
        }
    }

    @DELETE
    @Path("/{postId}")
    public Response delPost(@PathParam("postId") int postId) {
        PostLibrary.getInstance().delPost(postId);
        return Response.status(200).build();
    }

    @PATCH
    @Path("/like/{postId}")
    public Response likePost(@PathParam("postId") int post_id, @Context HttpServletRequest request) {
        String current_userIdentifiant = (String) request.getSession().getAttribute("utilisateurConnecte");
        try {
            User currentUser = UserLibrary.getInstance().getUser(current_userIdentifiant);
            PostLibrary.getInstance().addLikeToPost(post_id,currentUser.getId());
            return Response.status(200).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(409).build();
        }
    }

    @PATCH
    @Path("/dislike/{postId}")
    public Response dislikePost(@PathParam("postId") int post_id, @Context HttpServletRequest request) {
        String current_userIdentifiant = (String) request.getSession().getAttribute("utilisateurConnecte");
        try {
            User currentUser = UserLibrary.getInstance().getUser(current_userIdentifiant);
            PostLibrary.getInstance().delLikeToPost(post_id,currentUser.getId());
            return Response.status(200).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(409).build();
        }
    }
}
