package pl.edu.pjwstk.jaz.auction.image;


import pl.edu.pjwstk.jaz.auction.AuctionRepository;
import pl.edu.pjwstk.jaz.utils.MyUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@ApplicationScoped
public class ImageRepository {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private AuctionRepository auctionRepository;

    @Transactional
    public ImageEntity getImage(String name) {
        // DOESN'T WORK
        return em.find(ImageEntity.class, name);
    }

    @Transactional
    public boolean imageExists(String name) {
        return getImage(name) != null;
    }

    @Transactional
    public void saveImage(ImageEntity image) {
        em.persist(image);
    }

    @Transactional
    public void removeImage(ImageEntity image) {

        MyUtils.removeFile(image.getFilePath());

        em.remove(em.merge(image));
    }

    @Transactional
    public ImageEntity createImageInDatabase(String fileExtension) {
        ImageEntity image = new ImageEntity();

        do {
            image.setName(
                    MyUtils.randomName(20, fileExtension)
            );
        } while (imageExists(image.getName()));

        return image;
    }

    @Transactional
    public ImageEntity createImage(Part file) {

        ImageEntity resultImage = null;

        try (InputStream input = file.getInputStream()) {

            // exampleContentType = ["image", "png"]
            String[] contentType = file.getContentType().split("/");
            if (!contentType[0].equals("image")) {
                // ERROR FILE IS NOT AN IMAGE
                return null;
            }

            resultImage = createImageInDatabase(contentType[1]);

            Files.copy(
                    input, new File(
                            ImageEntity.FILES_PATH,
                            resultImage.getName()
                    ).toPath());

            System.out.println("Image has been saved successfully - " + resultImage.getName());

        } catch (IOException e) {
            assert resultImage != null;
            System.out.println("Something happened while saving image - " + resultImage.getName());
            e.printStackTrace();
        }

        return resultImage;
    }
}
