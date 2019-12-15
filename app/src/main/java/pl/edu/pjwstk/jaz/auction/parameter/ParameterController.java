package pl.edu.pjwstk.jaz.auction.parameter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class ParameterController implements Serializable {
    @Inject
    ParameterRepository parameterRepository;

    @Inject
    ParameterRequest parameterRequest;

    public List<ParameterEntity> getAllParameters() {
        return parameterRepository.getAllParameters();
    }

    public String commitParameters() {
        for (String s : parameterRequest.getSplitAddedParameters())
            parameterRepository.createParameter(s);

        for (String s : parameterRequest.getSplitRemovedParameters())
            parameterRepository.removeParameter(s);

        return "editparameters.xhtml?faces-redirect=true";
    }
}
