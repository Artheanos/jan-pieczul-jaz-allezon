package pl.edu.pjwstk.jaz.auction;

import pl.edu.pjwstk.jaz.utils.MyUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;


@Named
@RequestScoped
public class AuctionRequest {
    private String title;
    private String price;
    private String htmlFileContents;
    private Part files;
    private String category;
    private String deletedImages;
    private String parameterValues;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlFileContents() {
        return htmlFileContents;
    }

    public void setHtmlFileContents(String htmlFileContents) {
        this.htmlFileContents = htmlFileContents;
    }

    public Part getFiles() {
        return files;
    }

    public void setFiles(Part files) {
        this.files = files;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeletedImages() {
        return deletedImages;
    }

    public String[] getSplitDeletedImages() {
        return MyUtils.mySplit(deletedImages);
    }

    public void setDeletedImages(String deletedImages) {
        this.deletedImages = deletedImages;
    }

    public String getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(String parameterValues) {
        this.parameterValues = parameterValues;
    }
}
