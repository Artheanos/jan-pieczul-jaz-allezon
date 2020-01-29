package pl.edu.pjwstk.jaz.auction.parameter;

import pl.edu.pjwstk.jaz.auction.AuctionEntity;
import pl.edu.pjwstk.jaz.auction.parameter.auction_parameter.AuctionParameterEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> getAllParametersAsMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        getAllParameters().forEach(x -> result.put(x.getName(), x.getName()));
        return result;
    }

    public List<AuctionParameterEntity> getAuctionProfileEntitiesOfAuction(AuctionEntity auctionEntity) {
        return parameterRepository.getAuctionProfileEntities(auctionEntity);
    }

    public String commitParameters() {
        for (String s : parameterRequest.getSplitAddedParameters())
            parameterRepository.createParameter(s);

        for (String s : parameterRequest.getSplitRemovedParameters())
            parameterRepository.removeParameter(s);

        return "editparameters.xhtml?faces-redirect=true";
    }
}
