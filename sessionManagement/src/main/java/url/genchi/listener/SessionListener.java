package url.genchi.listener;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by mac on 2016/6/9.
 */
public class SessionListener implements HttpSessionListener {
    private final int sessionTimeToLiveMins = 2;
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("==== Session is create ====");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        event.getSession().setMaxInactiveInterval(sessionTimeToLiveMins * 60);
        event.getSession().setAttribute("ip", request.getRemoteAddr());
        event.getSession().setAttribute("agent", request.getHeader("User-Agent"));
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("==== Session is destroyed ====");
    }
}
