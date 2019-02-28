package servlets;

import entites.User;
import manager.UserLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/private/Search")
public class SearchServlet extends GenericServlet {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        //Get user
        String idCurrentUser = (String) req.getSession().getAttribute("utilisateurConnecte");
        User currentUser = null;
        try {
            currentUser = UserLibrary.getInstance().getUser(idCurrentUser);
        } catch (SQLException e) {
            LOG.error("Erreur lors de la récupération de l'utilisateur");
            e.printStackTrace();
        }

        //Get liste of users
        String search = req.getParameter("search");
        ArrayList<User> liste = UserLibrary.getInstance().searchUser(search);
        boolean noUser = false;
        if (liste == null || liste.size() == 0) { noUser = true; }

        //Add var to page
        context.setVariable("user",currentUser);
        context.setVariable("search",search);
        context.setVariable("noUser",noUser);
        context.setVariable("liste",liste);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("Search", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        String search = req.getParameter("rechercher");
        LOG.info("Recherche : {}",search);
        resp.sendRedirect("/private/Search?search="+search);
    }
}
