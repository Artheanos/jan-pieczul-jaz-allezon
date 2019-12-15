package pl.edu.pjwstk.jaz.login;

import pl.edu.pjwstk.jaz.auth.ProfileEntity;

import javax.faces.application.ResourceHandler;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebFilter("/*")
public class LoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Optional<ProfileEntity> user = Optional.ofNullable((ProfileEntity) req.getSession().getAttribute("user"));

        List<String> whitelist = List.of(
                req.getContextPath() + "/login.xhtml",
                req.getContextPath() + "/register.xhtml"
        );

        if (
                user.isPresent() ||
                        whitelist.contains(req.getRequestURI()) ||
                        req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/")
        )
            chain.doFilter(req, res);
        else
            res.sendRedirect("/app/login.xhtml");
    }
}
