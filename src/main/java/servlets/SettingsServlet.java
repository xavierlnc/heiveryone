package servlets;

import entites.User;
import manager.UserLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import utils.MotDePasseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/private/Settings")
@MultipartConfig
public class SettingsServlet extends GenericServlet{

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        String idCurrentUser = (String) req.getSession().getAttribute("utilisateurConnecte");
        User currentUser = null;
        try {
            currentUser = UserLibrary.getInstance().getUser(idCurrentUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        context.setVariable("user",currentUser);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("Settings", context, resp.getWriter());
    }

}
