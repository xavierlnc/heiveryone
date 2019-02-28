package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;


    public abstract class GenericServlet extends HttpServlet {

        protected TemplateEngine createTemplateEngine(ServletContext servletContext) {
            ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
            templateResolver.setCharacterEncoding("UTF-8");
            templateResolver.setPrefix("/WEB-INF/Templates/");
            templateResolver.setSuffix(".html");

            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
            templateEngine.addDialect(new Java8TimeDialect());

            return templateEngine;
        }
    }


