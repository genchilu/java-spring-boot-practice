package url.genchi.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mac on 2016/6/21.
 */
@WebFilter(filterName = "sessionCheckerFilter", urlPatterns = "/*")
public class SessionCheckerFilter implements Filter {
    Logger log = Logger.getLogger(SessionCheckerFilter.class);
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        if(session.getAttribute("ip") != null && !session.getAttribute("ip").toString().equals(request.getRemoteAddr())) {
            log.info("session ip is not equals to request ip");
        }
        if(session.getAttribute("agent") != null &&
                !session.getAttribute("agent").toString().equals(httpRequest.getHeader("User-Agent"))) {
            log.info("session agent is not equals to request agent");
        }
        chain.doFilter(request, response);
    }
    public void destroy() {

    }
}
