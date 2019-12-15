package pl.edu.pjwstk.jaz.login;

import pl.edu.pjwstk.jaz.auth.ProfileEntity;
import pl.edu.pjwstk.jaz.auth.ProfileRepository;
import pl.edu.pjwstk.jaz.login.LoginRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

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
//
//    public String logout() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.getExternalContext().getSessionMap().remove("user", loginRequest.getUsername());
//        return "index";
//    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ProfileEntity profile;

        profile = profileRepository.getProfile("username", loginRequest.getUsername());

        if (profile == null) {
            errorMessage = "Wrong username";
            return "login";
        }

        if (!profileRepository.profileMatchesPassword(profile, loginRequest.getPassword())) {
            errorMessage = "Wrong password";
            return "login";
        }
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        sessionMap.put("user", profile);
        return "myauctions?faces-redirect=true";
    }
}