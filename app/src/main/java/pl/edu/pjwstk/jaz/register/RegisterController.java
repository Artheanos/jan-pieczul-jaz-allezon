package pl.edu.pjwstk.jaz.register;

import pl.edu.pjwstk.jaz.auth.ProfileEntity;
import pl.edu.pjwstk.jaz.auth.ProfileRepository;
import pl.edu.pjwstk.jaz.register.RegisterRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterController {

    @Inject
    private RegisterRequest registerRequest;

    @Inject
    private ProfileRepository profileRepository;

    private String errorMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isFailed() {
        return !errorMessage.isEmpty();
    }

    public String register() {

        if (profileRepository.profileExists("username", registerRequest.getUsername())) {
            errorMessage = "Username is already taken";
            return "register";
        }

        if (profileRepository.profileExists("email", registerRequest.getEmail())) {
            errorMessage = "There is already a profile with that email";
            return "register";
        }

        ProfileEntity newProfile = profileRepository.createProfile(registerRequest);
        profileRepository.saveProfile(newProfile);

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("user", newProfile);

        return "myauctions";
    }
}
