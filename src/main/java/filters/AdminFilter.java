package filters;

import entites.User;
import manager.UserLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter("/private/admin/*")
public class AdminFilter implements Filter {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String identifiant = (String) httpRequest.getSession().getAttribute("utilisateurConnecte");

        User currentUser = null;
        try {
            currentUser = UserLibrary.getInstance().getUser(identifiant);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!currentUser.isAdmin()) {
            LOG.warn("Accès refusé");
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect("/private/Home");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
