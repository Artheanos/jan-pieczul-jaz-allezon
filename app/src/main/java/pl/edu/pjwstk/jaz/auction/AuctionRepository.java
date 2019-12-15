package pl.edu.pjwstk.jaz.auction;


import pl.edu.pjwstk.jaz.auction.image.ImageEntity;
import pl.edu.pjwstk.jaz.auction.image.ImageRepository;
import pl.edu.pjwstk.jaz.auction.section.category.CategoryEntity;
import pl.edu.pjwstk.jaz.auth.ProfileEntity;
import pl.edu.pjwstk.jaz.utils.MyUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

@ApplicationScoped
public class AuctionRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ImageRepository imageRepository;

    @Transactional
    public AuctionEntity getAuction(long id) {
        return em.find(AuctionEntity.class, id);
    }

    @Transactional
    public void saveAuction(AuctionEntity auctionEntity) {
        em.persist(auctionEntity);
    }

    @Transactional
    public void updateAuction(AuctionEntity newAuction) {
        AuctionEntity realAuction = em.find(AuctionEntity.class, newAuction.getId());

        if (newAuction.getTitle() != null)
            realAuction.setTitle(newAuction.getTitle());
        if (newAuction.getPrice() != null)
            realAuction.setPrice(newAuction.getPrice());
        if (newAuction.getHtmlFileName() != null)
            realAuction.setHtmlFileName(newAuction.getHtmlFileName());
        if (newAuction.getImages() != null)
            realAuction.setImages(newAuction.getImages());
        if (newAuction.getOwner() != null)
            realAuction.setOwner(newAuction.getOwner());
//        if (newAuction.getCategory() != null)
//            realAuction.setCategory(newAuction.getCategory());
    }

    @Transactional
    public void updateAuction(AuctionEntity newAuction, long id) {
        newAuction.setId(id);
        updateAuction(newAuction);
    }

    @Transactional
    public void removeAuction(AuctionEntity auction) {

        for (ImageEntity image : auction.getImages())
            imageRepository.removeImage(image);

        auction.removeHtmlFile();
        em.remove(em.merge(auction));
    }

    @Transactional
    public void addImageToAuction(AuctionEntity auction, ImageEntity image) {
        auction.addImage(image);
        em.persist(image);
    }

    @Transactional
    public void createAuction() {

    }

    @Transactional
    public List<AuctionEntity> getProfileAuctions(ProfileEntity profileEntity) {
        return em.createQuery(
                "from AuctionEntity where owner = :_profile",
                AuctionEntity.class
        ).setParameter("_profile", profileEntity).getResultList();
    }

    @Transactional
    public List<AuctionEntity> getAllAuctions() {
        return em.createQuery(
                "from AuctionEntity",
                AuctionEntity.class
        ).getResultList();
    }

    @Transactional
    public boolean htmlFileExists(String htmlFileName) {
        List<AuctionEntity> queryList = em.createQuery(
                "from AuctionEntity where htmlFileName = :_htmlFileName",
                AuctionEntity.class
        ).setParameter("_htmlFileName", htmlFileName).getResultList();

        return !queryList.isEmpty();
    }

    @Transactional
    public String createHtmlFile(String contents) {
        String htmlFileName;
        File htmlFile;

        do {
            htmlFileName = MyUtils.randomName(20, "html");
            htmlFile = new File(MyUtils.joinPaths(AuctionEntity.HTML_FILES_PATH, htmlFileName));
        } while (htmlFile.exists() && htmlFileExists(htmlFileName));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile));
            writer.write(contents);
            writer.close();
            System.out.println("File " + htmlFileName + " has been saved successfully");

        } catch (IOException e) {
            System.out.println("There was a problem while saving " + htmlFileName);
            e.printStackTrace();
        }

        return htmlFileName;
    }
}
