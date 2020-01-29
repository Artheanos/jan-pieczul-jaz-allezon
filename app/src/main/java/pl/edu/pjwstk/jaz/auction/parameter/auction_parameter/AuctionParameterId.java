package pl.edu.pjwstk.jaz.auction.parameter.auction_parameter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuctionParameterId implements Serializable {
    private Long auctionId;
    private Long parameterId;

    public AuctionParameterId() {
    }

    public AuctionParameterId(Long auctionId, Long parameterId) {
        this.auctionId = auctionId;
        this.parameterId = parameterId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionParameterId auctionParameterId = (AuctionParameterId) o;
        return Objects.equals(auctionId, auctionParameterId.auctionId) &&
                Objects.equals(parameterId, auctionParameterId.parameterId);
    }
}
