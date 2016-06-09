package url.genchi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.listener.SessionListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by mac on 2016/6/7.
 */
@RestController
@SpringBootApplication
public class Application implements ServletContextInitializer{

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String test(HttpSession session) {
        return session.getId();
    }
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setName("id");
        servletContext.addListener(new SessionListener());
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
