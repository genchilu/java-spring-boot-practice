package url.genchi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.listener.SessionListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by mac on 2016/6/7.
 */
@RestController
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ServletComponentScan(basePackages = {"url.genchi.filter"})
public class Application implements ServletContextInitializer{
    private final int freshMuns = 1;

    @RequestMapping(value="/secure", method = RequestMethod.GET)
    @ResponseBody
    public String secure(HttpServletRequest request) {
        return "you are allowed to access normal secure info";
    }

    @RequestMapping(value="/secure/more", method = RequestMethod.GET)
    @ResponseBody
    public String moreSecure(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if((System.currentTimeMillis() - session.getCreationTime()) > freshMuns*60*1000){
            return "you are allowed to access more secure info";
        } else {
            return "permission denied";
        }
    }

    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setName("id");
        servletContext.getSessionCookieConfig().setDomain("localhost");
        servletContext.getSessionCookieConfig().setHttpOnly(true);
        servletContext.getSessionCookieConfig().setMaxAge(-1);
        servletContext.getSessionCookieConfig().setSecure(true);
        servletContext.addListener(new SessionListener());
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
