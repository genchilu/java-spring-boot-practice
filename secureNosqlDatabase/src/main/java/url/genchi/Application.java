package url.genchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.entities.User;
import url.genchi.repository.read.MongoReadRepository;
import url.genchi.repository.write.MongoWriteRepository;

/**
 * Created by genchilu on 2016/5/3.
 */
@RestController
@SpringBootApplication
public class Application {
//    @Autowired
//    MongoWriteRepository _writeRepository;
//    @Autowired
//    MongoReadRepository _readRepository;
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        MongoWriteRepository writeRepository = ctx.getBean(MongoWriteRepository.class);
        User user1 = new User("222", "222");
        writeRepository.save(user1);
        MongoReadRepository ReadRepository = ctx.getBean(MongoReadRepository.class);
        User user2 = new User("222u", "222u");
        ReadRepository.save(user2);
    }
}
