package Controllers;

import Exceptions.IdentifiantAlreadyExistsException;
import Exceptions.IdentifiantOrPassIncorrectException;
import Exceptions.InputsEmptyException;
import Exceptions.UserDeletesHimselfException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entites.User;
import manager.UserLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/users")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newUser(MultivaluedMap<String, String> params ,@Context HttpServletRequest request) {

        //Get params
        String name = params.get("name").get(0);
        String lastName = params.get("lastName").get(0);
        String dob = params.get("dob").get(0);
        String mail = params.get("mail").get(0);
        String identifiant = params.get("identifiant").get(0);
        String pass = params.get("pass").get(0);

        //Add user
        try {
            UserLibrary.getInstance().addUser(name,lastName,dob,mail,identifiant,pass);
            if (params.get("inscriptionPerso").get(0).equals("true")) {
                request.getSession().setAttribute("utilisateurConnecte",identifiant);
            }
            return Response.status(200).build();
        } catch (IdentifiantAlreadyExistsException e) {
            return Response.status(204).build();
        } catch (SQLException e) {
            return Response.status(409).build();
        } catch (InputsEmptyException e) {
            return Response.status(205).build();
        }
    }

    @Path("/{targetUserId}")
    @DELETE
    public Response delUser(@PathParam("targetUserId") int target_userId, @Context HttpServletRequest request) {
        //Get connected user
        HttpSession session = request.getSession();
        String currentUser_identifiant = (String) session.getAttribute("utilisateurConnecte");

        //Try to delete
        try {
            User currentUser = UserLibrary.getInstance().getUser(currentUser_identifiant);
            UserLibrary.getInstance().delUser(currentUser.getId(),target_userId);
            return Response.status(200).build();
        } catch (UserDeletesHimselfException e) {
            LOG.warn("{} essaye de se supprimer !", currentUser_identifiant);
            return Response.status(204).build();
        } catch (SQLException e) {
            return Response.status(409).build();
        }
    }

    @POST
    @Path("/deconnexion")
    public Response deconnexion(@Context HttpServletRequest request) {
        request.getSession().removeAttribute("utilisateurConnecte");
        return Response.status(200).build();
    }

    @POST
    @Path("/connexion")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response connexion(@Context HttpServletRequest request,MultivaluedMap<String, String> paramsValue) {
        try {
            UserLibrary.getInstance().verifyUserInfos(paramsValue.get("identifiant").get(0),paramsValue.get("pass").get(0));
            request.getSession().setAttribute("utilisateurConnecte",paramsValue.get("identifiant").get(0));
            return Response.status(200).build();
        } catch (IdentifiantOrPassIncorrectException e) {
            return Response.status(201).build();
        } catch (SQLException e) {
            return Response.status(410).build();
        }
    }

    @Path("/searchUser/{search}")
    @GET
    public String searchUser(@PathParam("search") String search) throws JsonProcessingException {
        ArrayList<User> userList = new ArrayList<>();
        userList.addAll(UserLibrary.getInstance().searchUser(search));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userList);
    }

    @Path("/updateUserStatus/{targetUserId}")
    @PATCH
    public Response updateUserStatus(@PathParam("targetUserId") int id, MultivaluedMap<String, String> paramsValue) {
        UserLibrary.getInstance().updateUserStatus(id,paramsValue.get("status").get(0));
        LOG.info("Un utilisateur a vu son statut changé : {}",id);
        return Response.status(200).build();
    }

    @Path("/updateUser")
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateUser(@Context HttpServletRequest request, MultivaluedMap<String, String> params) {
        //Get user
        String identifiant = (String) request.getSession().getAttribute("utilisateurConnecte");

        //Get params
        String name = params.get("name").get(0);
        String lastName = params.get("lastName").get(0);
        String dob = params.get("dob").get(0);
        String description = params.get("description").get(0);
        String theme = params.get("theme").get(0);
        String mail = params.get("mail").get(0);
        String oldPass = params.get("oldPass").get(0);
        String newPass = params.get("newPass").get(0);
        String confNewPass = params.get("confNewPass").get(0);

        try {
            UserLibrary.getInstance().updateUser(name,lastName,dob,description,theme,mail,identifiant,oldPass,newPass,confNewPass);
            return Response.status(200).build();
        } catch (SQLException e) {
            return Response.status(409).build();
        }
    }

    @Path("/stalk")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addStalkToUser(@Context HttpServletRequest request, MultivaluedMap<String, String> paramsValue) {

        int target_userId = Integer.valueOf(paramsValue.get("target_userId").get(0));
        //Get current user
        String current_userIdentifiant = (String) request.getSession().getAttribute("utilisateurConnecte");

        try {
            User currentUser = UserLibrary.getInstance().getUser(current_userIdentifiant);
            UserLibrary.getInstance().addStalkToUser(currentUser.getId(),target_userId);
            return Response.status(200).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(410).build();
        }
    }

    @Path("/stalk")
    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response delStalkOfUser(@Context HttpServletRequest request, MultivaluedMap<String, String> paramsValue) {

        int target_userId = Integer.valueOf(paramsValue.get("target_userId").get(0));
        //Get current user
        String current_userIdentifiant = (String) request.getSession().getAttribute("utilisateurConnecte");

        try {
            User currentUser = UserLibrary.getInstance().getUser(current_userIdentifiant);
            UserLibrary.getInstance().delStalkOfUser(currentUser.getId(),target_userId);
            return Response.status(200).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(410).build();
        }
    }

}