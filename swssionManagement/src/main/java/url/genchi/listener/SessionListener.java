package url.genchi.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by mac on 2016/6/9.
 */
public class SessionListener implements HttpSessionListener {
    private final int sessionTimeToLiveMins = 2;
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("==== Session is create ====");
        event.getSession().setMaxInactiveInterval(sessionTimeToLiveMins * 60);
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("==== Session is destroyed ====");
    }
}
