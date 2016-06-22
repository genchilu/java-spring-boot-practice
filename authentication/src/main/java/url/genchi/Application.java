package url.genchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
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
    @ResponseBody
    public String signin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(_userRepo.getByUsername(username) != null) {
            return "user is already exist";
        } else if(WorstPassword.getInstance().isWorstPassword(password)){
            return "weak password";
        }
        _userRepo.save(new User(username, Password.encoder.encode(password), "USER"));
        return "Create user success";
    }
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ResponseBody
//    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
//                        HttpServletRequest request) {
//        return username + " " + password;
//        String ip = request.getRemoteAddr();
//        if(Banuser.isInAuthentication(ip)) {
//            return "Authentication already in progress";
//        }
//        if(!Banuser.isAllow(ip)) {
//            return "you have been blocked!";
//        }
//        User user = _userRepo.getByUsername(username);
//        if(user == null) {
//            return "user is not exist";
//        }
//        boolean isValidate = Password.isValidatePassword(password, user.getPassword());
//        if(isValidate) {
//            Banuser.successfulAttempt(ip);
//            return "success login";
//        } else {
//            Banuser.failedAttempt(ip);
//            return "your username or password isn't correct";
//        }
//    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
