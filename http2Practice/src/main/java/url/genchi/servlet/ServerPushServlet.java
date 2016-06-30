package url.genchi.servlet;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mac on 2016/6/30.
 */
@WebServlet(urlPatterns="/serverpush")
public class ServerPushServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(ServerPushServlet.class);
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Request jettyRequest = (Request) req;
        if (jettyRequest.isPushSupported()) {
            jettyRequest.getPushBuilder()
                    .path("/1.jpeg")
                    .push();
        } else {
            logger.info("non http2");
        }
        resp.getWriter().write("<html><img src=\"1.jpeg\"></html>");
    }
}