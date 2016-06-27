package url.genchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.entities.User;
import url.genchi.password.Password;
import url.genchi.password.WorstPassword;
import url.genchi.repositories.UserRepository;

/**
 * Created by mac on 2016/6/5.
 */
@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"url.genchi.configs", "url.genchi.validate.signin", "url.genchi.listener",
        "url.genchi.service", "url.genchi.authentication"})
@ServletComponentScan
public class Application{
    @Autowired
    private UserRepository _userRepo;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(_userRepo.getByUsername(username) != null) {
            return "user is already exist";
        } else if(WorstPassword.getInstance().isWorstPassword(password)){
            return "weak password";
        }
        _userRepo.save(new User(username, Password.encoder.encode(password), "USER"));
        return "Create user success";
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
