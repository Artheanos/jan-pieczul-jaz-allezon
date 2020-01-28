package pl.edu.pjwstk.jaz.auction.parameter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import pl.edu.pjwstk.jaz.utils.MyUtils;

@Named
@RequestScoped
public class ParameterRequest {
    String addedParameters;
    String removedParameters;

    public String getAddedParameters() {
        return addedParameters;
    }

    public String getRemovedParameters() {
        return removedParameters;
    }

    public void setAddedParameters(String addedParameters) {
        this.addedParameters = addedParameters;
    }

    public void setRemovedParameters(String removedParameters) {
        this.removedParameters = removedParameters;
    }

    public String[] getSplitAddedParameters() {
        return MyUtils.mySplit(addedParameters);
    }

    public String[] getSplitRemovedParameters() {
        return MyUtils.mySplit(removedParameters);
    }
}
