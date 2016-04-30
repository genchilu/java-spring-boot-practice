package url.genchi.sql.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import url.genchi.sql.hibernate.daos.read.UserReadDao;
import url.genchi.sql.hibernate.daos.write.UserWriteDao;
import url.genchi.sql.hibernate.entities.User;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private UserWriteDao _userDao;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam("email") String email, @RequestParam("name") String name) {
        try {
            User user = new User(email, name);
            //_userDao.saveAtWriteSession(user);
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
    public String get(@RequestParam("email") String email) {
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
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        UserReadDao userReadDao = ctx.getBean(UserReadDao.class);
        UserWriteDao userWriteDao = ctx.getBean(UserWriteDao.class);
        User user1 = new User("test2", "test2");
        User user2 = new User("test2", "test2");
        userWriteDao.save(user1);
        userReadDao.save(user2);
    }

}
