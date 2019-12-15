package pl.edu.pjwstk.jaz.auction.parameter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


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

    private String[] mySplit(String s) {
        if (s.strip().isEmpty())
            return new String[]{};
        return s.split("[|]");
    }

    public void setRemovedParameters(String removedParameters) {
        this.removedParameters = removedParameters;
    }

    public String[] getSplitAddedParameters() {
        return mySplit(addedParameters);
    }

    public String[] getSplitRemovedParameters() {
        return mySplit(removedParameters);
    }
}
