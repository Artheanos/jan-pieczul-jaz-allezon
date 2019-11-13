package pl.edu.pjwstk.jaz.webapp;

import pl.edu.pjwstk.jaz.auth.ProfileEntity;
import pl.edu.pjwstk.jaz.auth.ProfileRepository;
import pl.edu.pjwstk.jaz.login.LoginRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginController {
    @Inject
    private LoginRequest loginRequest;

    @Inject
    private ProfileRepository profileRepository;

    private String errorMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isFailed() {
        return !errorMessage.isEmpty();
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ProfileEntity profile;

        profile = profileRepository.getProfile("username", loginRequest.getUsername());

        if (profile == null) {
            errorMessage = "Wrong username";
            return "login";
        }

        if (profileRepository.profileMatchesPassword(profile, loginRequest.getPassword())) {
            context.getExternalContext().getSessionMap().put("user", loginRequest.getUsername());
            return "welcome";
        } else {
            errorMessage = "Wrong password";
            return "login";
        }
    }
}