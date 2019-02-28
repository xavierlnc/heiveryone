package servlets;

import Exceptions.InputsEmptyException;
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
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/private/Home")
public class HomeServlet extends GenericServlet {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        String idCurrentUser = (String) req.getSession().getAttribute("utilisateurConnecte");
        User currentUser = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            currentUser = UserLibrary.getInstance().getUser(idCurrentUser);
            users = UserLibrary.getInstance().propositionOfUser(currentUser, 4);
        } catch (SQLException e) {
            LOG.error("Erreur lors de la récupération de l'utilisateur");
            e.printStackTrace();
        }

        //Load data
        ArrayList<Post> posts = PostLibrary.getInstance().getListPostOfStalks(currentUser);
        HashMap<Integer,Boolean> isExistaLikeOfUserOnPost = PostLibrary.getInstance().setUserLikeOnPost(posts, currentUser.getId());

        //Add var to page
        context.setVariable("posts",posts);
        context.setVariable("isExistaLikeOfUserOnPost",isExistaLikeOfUserOnPost);
        context.setVariable("user",currentUser);
        context.setVariable("users",users);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("Home", context, resp.getWriter());
    }

}
