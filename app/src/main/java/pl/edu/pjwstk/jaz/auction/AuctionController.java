package pl.edu.pjwstk.jaz.auction;

import pl.edu.pjwstk.jaz.auction.image.ImageEntity;
import pl.edu.pjwstk.jaz.auction.image.ImageRepository;
import pl.edu.pjwstk.jaz.auction.section.SectionController;
import pl.edu.pjwstk.jaz.auction.section.category.CategoryEntity;
import pl.edu.pjwstk.jaz.auction.section.category.CategoryRepository;
import pl.edu.pjwstk.jaz.auth.ProfileEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class AuctionController implements Serializable {
    @Inject
    private AuctionRequest auctionRequest;
    @Inject
    private AuctionRepository auctionRepository;
    @Inject
    private ImageRepository imageRepository;
    @Inject
    private CategoryRepository categoryRepository;

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        /* https://stackoverflow.com/a/36503203 */
        if (part == null)
            return Collections.emptyList();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    private String errorMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isFailed() {
        return !errorMessage.isEmpty();
    }

    public String upload() throws ServletException, IOException {

        CategoryEntity categoryEntity = null;
        if (auctionRequest.getCategory() != null && !auctionRequest.getCategory().isEmpty())
            categoryEntity = categoryRepository.getCategoryByName(auctionRequest.getCategory());

        var newAuction = new AuctionEntity(
                auctionRequest.getTitle(),
                auctionRequest.getPrice(),
                auctionRepository.createHtmlFile(auctionRequest.getHtmlFileContents()),
                (ProfileEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"),
                categoryEntity
        );

        auctionRepository.saveAuction(newAuction);

        for (Part imageFile : getAllParts(auctionRequest.getFiles())) {
            ImageEntity image = imageRepository.createImage(imageFile);
            auctionRepository.addImageToAuction(newAuction, image);
        }

        return "myauctions?faces-redirect=true";
    }

    public String update(/* Auction id is taken from requestParameterMap */) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        long id = Long.parseLong(params.get("auction_id_parameter"));

        AuctionEntity newAuction = new AuctionEntity();

        newAuction.setTitle(auctionRequest.getTitle());
        newAuction.setCategory(categoryRepository.getCategoryByName(auctionRequest.getCategory()));

        // TODO updating auction's html works by always creating a new html file and deleting the old one
        newAuction.setHtmlFileName(
                auctionRepository.createHtmlFile(auctionRequest.getHtmlFileContents())
        );

        auctionRepository.updateAuction(newAuction, id);

        return "myauctions?faces-redirect=true";
    }

    public List<AuctionEntity> getAllAuctions() {
        return auctionRepository.getAllAuctions();
    }

    public List<AuctionEntity> getSessionProfileAuctions() {
        return auctionRepository.getProfileAuctions(
                (ProfileEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user")
        );
    }

    public List<AuctionEntity> getProfileAuctions(ProfileEntity profile) {
        return auctionRepository.getProfileAuctions(profile);
    }

    public AuctionEntity getAuction(long id) {
        return auctionRepository.getAuction(id);
    }

    public String remove(/* Auction id is taken from requestParameterMap */) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        auctionRepository.removeAuction(auctionRepository.getAuction(Long.parseLong(params.get("auction_id_parameter"))));
        return "myauctions";
    }
}
