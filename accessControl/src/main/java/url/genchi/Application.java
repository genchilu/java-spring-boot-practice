package url.genchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import url.genchi.entities.MyUser;

import java.util.Set;

/**
 * Created by mac on 2016/6/12.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@RestController
@ComponentScan
public class Application {
    @RequestMapping(value="/profile/{user}", method = RequestMethod.GET)
    public String getUserProfile(@PathVariable("user") String user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        User loginUser = (User) auth.getPrincipal();
        if(roles.contains("ROLE_ADMIN") || loginUser.getUsername().equals(user)) {
            return "get " + user + " profile";
        }
        return "permission deny";
    }
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public String getUser() {
        return "get user info";
    }
    @RequestMapping(value="/user", method = RequestMethod.DELETE)
    public String delUser() {
        return "del user";
    }
    @RequestMapping(value="/register", method= RequestMethod.POST)
    public String register(@RequestParam(name = "user") String user, @RequestParam(name = "isAdmin", required = false,
            defaultValue = "false") Boolean isAdmin) {
        //MyUser registerUser = new MyUser(user, isAdmin); //It's not security to save isAdmin directly
        MyUser registerUser = new MyUser(user, false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        // save user to db ...
        if(isAdmin && roles.contains("ROLE_ADMIN")) {
            return "save admin user successful";
        }
        return "saving normal user successful";
    }
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }
}
