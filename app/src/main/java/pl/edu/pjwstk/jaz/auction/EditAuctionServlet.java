package pl.edu.pjwstk.jaz.auction;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("edit")
public class EditAuctionServlet extends HttpServlet {

    @Inject
    AuctionRepository auctionRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long auction_id = Long.parseLong(req.getParameter("auction_id"));

        if (auctionRepository.getAuction(auction_id) != null) {
            resp.sendRedirect("edit.xhtml?auction_id=" + auction_id);
        } else {
            resp.getWriter().println("ERROR AUCTION " + auction_id + " NOT FOUND");
        }

    }
}
