package pl.edu.pjwstk.jaz.auction;

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
}
