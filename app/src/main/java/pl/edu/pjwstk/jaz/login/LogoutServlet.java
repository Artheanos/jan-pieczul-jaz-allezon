package pl.edu.pjwstk.jaz.login;


import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("logout")
public class LogoutServlet extends HttpServlet {

    @Inject
    LoginController loginController;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.getSession().removeAttribute("user");

        resp.sendRedirect("/app/index.html");
    }
}
