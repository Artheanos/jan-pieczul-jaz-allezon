package pl.edu.pjwstk.jaz.login;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/")
public class LoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String user = (String) req.getSession().getAttribute("user");
        if (!user.isEmpty())
            chain.doFilter(req, res);
        else
            res.sendRedirect("http://google.com");
    }
}
