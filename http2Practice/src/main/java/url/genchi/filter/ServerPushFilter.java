package url.genchi.filter;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.PushBuilder;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.annotation.ManagedObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by mac on 2016/6/30.
 */
//@ManagedObject("Push cache based on the HTTP 'Referer' header")
@WebFilter(filterName = "serverPushFilter", urlPatterns = "/index.html")
public class ServerPushFilter implements Filter {
    Logger logger = Logger.getLogger(ServerPushFilter.class);
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Request jettyRequest = (Request) request;
        if (jettyRequest.isPushSupported()) {
            logger.info("server push");
            for(int i=1;i<20;i++) {
                jettyRequest.getPushBuilder()
                        .path("/" + Integer.toString(i) + ".jpeg")
                        .push();
            }
        } else {
            logger.info("non http2");
        }
        chain.doFilter(request, response);
    }
    public void destroy() {

    }
}
