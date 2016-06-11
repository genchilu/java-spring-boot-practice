package url.genchi.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mac on 2016/6/11.
 */
@WebFilter(filterName="cacheController",urlPatterns="/*")
public class CacheControllerFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Cache-Control", "no-cache=\"Set-Cookie, Set-Cookie2\"");
        chain.doFilter(request, response);
    }
    public void destroy() {

    }
}
