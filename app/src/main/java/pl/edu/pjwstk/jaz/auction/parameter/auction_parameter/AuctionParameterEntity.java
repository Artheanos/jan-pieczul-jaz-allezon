package pl.edu.pjwstk.jaz.auction.parameter.auction_parameter;


import pl.edu.pjwstk.jaz.auction.AuctionEntity;
import pl.edu.pjwstk.jaz.auction.parameter.ParameterEntity;

import javax.persistence.*;

@Entity
@Table(name = "auction_parameter")
public class AuctionParameterEntity {
    @EmbeddedId
    private AuctionParameterId auctionParameterId;

    @ManyToOne
    @MapsId("auctionId")
    @JoinColumn(name = "auction_id")
    private AuctionEntity auction;

    @ManyToOne
    @MapsId("parameterId")
    @JoinColumn(name = "parameter_id")
    private ParameterEntity parameter;

    private String value;

    public AuctionParameterEntity() {
    }

    public AuctionParameterEntity(AuctionEntity auction, ParameterEntity parameter, String varchar) {
        this.auction = auction;
        this.parameter = parameter;
        this.value = varchar;
        this.auctionParameterId = new AuctionParameterId(auction.getId(), parameter.getId());
    }

    public AuctionParameterId getAuctionParameterId() {
        return auctionParameterId;
    }

    public void setAuctionParameterId(AuctionParameterId auctionParameterId) {
        this.auctionParameterId = auctionParameterId;
    }

    public AuctionEntity getAuction() {
        return auction;
    }

    public void setAuction(AuctionEntity auctionId) {
        this.auction = auctionId;
    }

    public ParameterEntity getParameter() {
        return parameter;
    }

    public void setParameter(ParameterEntity parameterId) {
        this.parameter = parameterId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}