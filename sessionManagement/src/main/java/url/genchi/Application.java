package url.genchi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.listener.SessionListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 * Created by mac on 2016/6/7.
 */
@RestController
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ServletComponentScan(basePackages = "url.genchi.filter")
public class Application implements ServletContextInitializer{
    private final int freshMuns = 1;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return "hello";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(HttpSession session) {
        session.setAttribute("isLogin", true);
        return "success login";
    }
    @RequestMapping(value="/secure", method = RequestMethod.GET)
    @ResponseBody
    public String secure(HttpSession session) {
        if(session.getAttribute("isLogin") != null && Boolean.valueOf(session.getAttribute("isLogin").toString())) {
            return "you are allowed to access normal secure info";
        } else {
            return "permission denied";
        }
    }
    @RequestMapping(value="/secure/more", method = RequestMethod.GET)
    @ResponseBody
    public String moreSecure(HttpSession session) {
        if((System.currentTimeMillis() - session.getLastAccessedTime()) < freshMuns*60*1000){
            return "you are allowed to access more secure info";
        } else {
            return "permission denied";
        }
    }
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    @ResponseBody
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
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
