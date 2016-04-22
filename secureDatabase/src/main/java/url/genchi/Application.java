package url.genchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import url.genchi.models.User;
import url.genchi.models.UserDao;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private UserDao _userDao;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam("email") String email, @RequestParam("name") String name) {
        try {
            User user = new User(email, name);
            _userDao.saveAtWriteSession(user);
          }
          catch(Exception ex) {
            return ex.getMessage();
          }
          return "User succesfully saved!";
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public String get(@RequestParam("id") long id) {
        User user = new User();
        try {
            user = _userDao.getById(id);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return user.toString();
    }

    @RequestMapping(value = "/getbymail", method = RequestMethod.POST)
    public String getByMail(@RequestParam("email") String email) {
        User user = new User();
        try {
            user = _userDao.getByEmail(email);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return user.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
