package servlets;

import entites.Post;
import entites.User;
import manager.PostLibrary;
import manager.UserLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/private/account")
public class AccountServlet extends GenericServlet {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        //Get parameters
        String idCurrentUser = (String) req.getSession().getAttribute("utilisateurConnecte");
        int targetUserId = Integer.valueOf(req.getParameter("id"));

        User targetUser = null;
        User currentUser = null;
        HashMap<String, Boolean> result = new HashMap<>();
        HashMap<String, Integer> stalksInfo = new HashMap<>();

        try {
            targetUser = UserLibrary.getInstance().getUser(targetUserId);
            currentUser = UserLibrary.getInstance().getUser(idCurrentUser);
            result = UserLibrary.getInstance().accountPageManager(currentUser,targetUser);
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("Erreur lors de la récupération de l'utilisateur");
        }


        //Get & set information to page
        ArrayList<Post> posts = PostLibrary.getInstance().getListPostOfUser(targetUser);
        HashMap<Integer,Boolean> isExistaLikeOfUserOnPost = PostLibrary.getInstance().setUserLikeOnPost(posts, currentUser.getId());

        context.setVariable("user", currentUser);
        context.setVariable("targetUser", targetUser);
        context.setVariable("posts", posts);
        context.setVariable("isExistaLikeOfUserOnPost",isExistaLikeOfUserOnPost);
        context.setVariable("same", result.get("same"));
        context.setVariable("stalk",result.get("stalk"));
        context.setVariable("description",result.get("description"));
        context.setVariable("numberOfPosts",UserLibrary.getInstance().stalksInfo(targetUser).get("numberOfPosts"));
        context.setVariable("numberOfStalks",UserLibrary.getInstance().stalksInfo(targetUser).get("numberOfStalks"));

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("Account", context, resp.getWriter());
    }


}
