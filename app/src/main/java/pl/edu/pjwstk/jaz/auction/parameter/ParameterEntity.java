package pl.edu.pjwstk.jaz.auction.parameter;

import pl.edu.pjwstk.jaz.auction.parameter.auction_parameter.AuctionParameterEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parameter")
public class ParameterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parameter")
    Set<AuctionParameterEntity> auctionParameterEntities = new HashSet<>();

    private String name;

    public ParameterEntity(String name) {
        this.name = name;
    }

    public ParameterEntity() {

    }

    public void addAuctionParameter(AuctionParameterEntity auctionParameterEntity) {
        auctionParameterEntities.add(auctionParameterEntity);
        auctionParameterEntity.setParameter(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AuctionParameterEntity> getAuctionParameterEntities() {
        return auctionParameterEntities;
    }

    public void setAuctionParameterEntities(Set<AuctionParameterEntity> auctionParameterEntities) {
        this.auctionParameterEntities = auctionParameterEntities;
    }
}
