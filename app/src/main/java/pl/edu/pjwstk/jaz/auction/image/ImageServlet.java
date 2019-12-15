package pl.edu.pjwstk.jaz.auction.image;

import pl.edu.pjwstk.jaz.utils.MyUtils;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("image")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String imageName = req.getParameter("name");
        String imagePath = MyUtils.joinPaths(ImageEntity.FILES_PATH, imageName);

        String[] tmp = imageName.split("\\.");
        String imageExtension = tmp[tmp.length - 1];

        resp.setContentType("image/" + imageExtension);

        OutputStream outputStream = resp.getOutputStream();

        ImageIO.write(
                ImageIO.read(new File(imagePath)),
                imageExtension,
                outputStream
        );

        outputStream.close();
    }
}
