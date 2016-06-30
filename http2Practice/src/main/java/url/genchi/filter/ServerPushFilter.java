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
    Logger log = Logger.getLogger(ServerPushFilter.class);
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (HttpVersion.fromString(request.getProtocol()).getVersion() < 20)
        {
            log.info("not http2");
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        PushBuilder pushBuilder = Request.getBaseRequest(request).getPushBuilder();
        for(int i=1;i<=20;i++) {
            pushBuilder.path("/" + Integer.toString(i) + ".jpeg").push();
        }
        chain.doFilter(request, response);
    }
    public void destroy() {

    }
}
