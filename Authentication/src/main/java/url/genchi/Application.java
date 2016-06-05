package url.genchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import url.genchi.encrypt.Encrypt;
import url.genchi.entities.User;
import url.genchi.repositories.UserRepository;
import url.genchi.worstpassword.WorstPassword;

/**
 * Created by mac on 2016/6/5.
 */
@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"url.genchi.configs", "url.genchi.validate.signin"})
public class Application {
    @Autowired
    private UserRepository _userRepo;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public String signin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(_userRepo.getByUsername(username) != null) {
            return "user is already exist";
        } else if(WorstPassword.getInstance().isWorstPassword(password)){
            return "weak password";
        }
        _userRepo.save(new User(username, Encrypt.encrypt(password)));
        return "Create user success";
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
