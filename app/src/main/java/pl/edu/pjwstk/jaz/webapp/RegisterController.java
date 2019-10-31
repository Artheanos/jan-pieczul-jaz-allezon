package pl.edu.pjwstk.jaz.webapp;

import pl.edu.pjwstk.jaz.register.RegisterRequest;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterController {
    @Inject
    private RegisterRequest registerRequest;

    public String register() {
        return "welcome";
    }
}
