package servlets;

import entites.Post;
import entites.User;
import manager.PostLibrary;
import manager.UserLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/private/delPost")
public class DelPostServlet extends HttpServlet {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        //Get post
        int postId = Integer.valueOf(req.getParameter("id"));
        Post post = null;
        try {
            post = PostLibrary.getInstance().getPost(postId);
        } catch (Exception e) {
            LOG.error("Erreur lors de la récupération du Post");
            e.printStackTrace();
        }

        //Get currentUser
        String idCurrentUser = (String) req.getSession().getAttribute("utilisateurConnecte");
        User currentUser = null;
        try {
            currentUser = UserLibrary.getInstance().getUser(idCurrentUser);
        } catch (SQLException e) {
            LOG.error("Erreur lors de la récupération de l'utilisateur");
            e.printStackTrace();
        }

        //Verify if the post author is the currentUser
        if (post.getAuthor().getId() == currentUser.getId()) {
            PostLibrary.getInstance().delPost(postId);
        }
        resp.sendRedirect("/private/Home");
    }
}
