package pl.edu.pjwstk.jaz.auction.image;

import pl.edu.pjwstk.jaz.auction.AuctionEntity;
import pl.edu.pjwstk.jaz.utils.MyUtils;

import javax.persistence.*;

import static pl.edu.pjwstk.jaz.utils.MyUtils.RESOURCES_PATH;

@Entity
@Table(name = "image")
public class ImageEntity {
    @Id
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auction_id")
    private AuctionEntity auction;

    final static public String FILES_PATH = MyUtils.joinPaths(RESOURCES_PATH, "images");

    public String getFilePath() {
        return MyUtils.joinPaths(FILES_PATH, getName());
    }

    public ImageEntity() {
    }

    public AuctionEntity getAuction() {
        return auction;
    }

    public void setAuction(AuctionEntity auction) {
        this.auction = auction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return "/app/image?name=" + getName();
    }
}
