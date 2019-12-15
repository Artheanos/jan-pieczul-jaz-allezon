package pl.edu.pjwstk.jaz.auction.section.category;

import pl.edu.pjwstk.jaz.auction.AuctionEntity;
import pl.edu.pjwstk.jaz.auction.section.SectionEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<AuctionEntity> auctions = new HashSet<>();

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public CategoryEntity(String name, SectionEntity section) {
        this.name = name;
        this.section = section;
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

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public Set<AuctionEntity> getAuctions() {
        return auctions;
    }

    public void setAuctions(Set<AuctionEntity> auctions) {
        this.auctions = auctions;
    }
}
