package pl.edu.pjwstk.jaz.auction;

import com.sun.istack.Nullable;
import pl.edu.pjwstk.jaz.auction.image.ImageEntity;
import pl.edu.pjwstk.jaz.auction.section.category.CategoryEntity;
import pl.edu.pjwstk.jaz.auth.ProfileEntity;
import pl.edu.pjwstk.jaz.utils.MyUtils;

import javax.persistence.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static pl.edu.pjwstk.jaz.utils.MyUtils.RESOURCES_PATH;

@Entity
@Table(name = "auction")
public class AuctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private Integer price;
    private String htmlFileName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "auction")
    private Set<ImageEntity> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private ProfileEntity owner;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    final static public String HTML_FILES_PATH = MyUtils.joinPaths(RESOURCES_PATH, "htmls");


    public AuctionEntity(
            String title,
            Integer price,
            String htmlFileName,
            ProfileEntity owner
    ) {
        this.title = title;
        this.price = price;
        this.htmlFileName = htmlFileName;
        this.owner = owner;
    }


    public AuctionEntity(
            String title,
            String price,
            String htmlFileName,
            ProfileEntity owner
    ) {
        this.title = title;
        setPrice(price);
        this.htmlFileName = htmlFileName;
        this.owner = owner;
    }


    public AuctionEntity(
            String title,
            String price,
            String htmlFileName,
            ProfileEntity owner,
            CategoryEntity category
    ) {
        this.title = title;
        setPrice(price);
        this.htmlFileName = htmlFileName;
        this.owner = owner;
        this.category = category;
    }

    public AuctionEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlFileName() {
        return htmlFileName;
    }

    public String getHtmlFileContents() throws IOException {
        return MyUtils.getFileContents(MyUtils.joinPaths(HTML_FILES_PATH, htmlFileName));
    }

    public void removeHtmlFile() {
        if (this.htmlFileName != null)
            MyUtils.removeFile(MyUtils.joinPaths(
                    HTML_FILES_PATH,
                    this.htmlFileName
            ));
    }

    public void setHtmlFileName(String htmlFileName) {
        if (htmlFileName.equals(this.htmlFileName))
            return;
        removeHtmlFile();
        this.htmlFileName = htmlFileName;
    }

    public Set<ImageEntity> getImages() {
        return images;
    }

    public void setImages(Set<ImageEntity> images) {
        this.images = images;
    }

    public void addImage(ImageEntity imageEntity) {
        images.add(imageEntity);
        imageEntity.setAuction(this);
    }

    public String getFirstImageName() {
        if (images.size() > 0)
            return images.iterator().next().getName();
        else
            return "DEFAULT.jpg";
    }

    public ProfileEntity getOwner() {
        return owner;
    }

    public void setOwner(ProfileEntity profile) {
        this.owner = profile;
    }

    public Integer getPrice() {
        return price;
    }

    public Double getPriceAsDouble() {
        return price.doubleValue() / 100;
    }

    public String getPrettyPrice() {
        String pennies = String.valueOf(price % 100);
        if (pennies.length() == 1)
            pennies += '0';
        return (price / 100) + "." + pennies + " zł";
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setPrice(Double price) {
        price *= 100;
        this.price = price.intValue();
    }

    public void setPrice(String price) {
        if (price.isEmpty()) {
            this.price = 0;
            return;
        }

        String[] priceSplit = price.replace(",", ".").split("\\.");
        System.out.println("priceSplit = " + Arrays.toString(priceSplit));

        if (priceSplit.length < 2) {
            this.price = Integer.parseInt(price) * 100;
            return;
        }

        int dollars = 0;
        int pennies = 0;

        if (!priceSplit[0].isEmpty())
            dollars = Integer.parseInt(priceSplit[0]);

        if (!priceSplit[1].isEmpty()) {
            if (priceSplit[1].length() == 1)
                pennies = Integer.parseInt(priceSplit[1]) * 10;
            else
                pennies = Integer.parseInt(priceSplit[1].substring(0, 2));
        }

        this.price = dollars * 100 + pennies;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

}
