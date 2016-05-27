package url.genchi;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.repository.read.MongoReadRepository;
import url.genchi.repository.write.MongoWriteRepository;

import java.net.UnknownHostException;

/**
 * Created by genchilu on 2016/5/3.
 */
@RestController
@SpringBootApplication
public class Application {
    @Autowired
    MongoWriteRepository _writeRepository;
    @Autowired
    MongoReadRepository _readRepository;
    @Autowired
    @Qualifier("read")
    MongoTemplate readMongoReadTemplate;

    //It would be dangerous while input is John%27;var%20date=new%20Date();%20do{curDate%20=%20new%20Date();}while(curDate-%20date<10000);var%20tmp=%271
    @RequestMapping(value = "/unsecure/count", method = RequestMethod.GET)
    public long countUnsecure(@RequestParam("name") String name) {
        DBObject query = (DBObject) JSON.parse("{$where: \"this.name == '" + name + "'\"}");
        long count = readMongoReadTemplate.getCollection("users").count(query);
        return count;
    }

    @RequestMapping(value = "/secure1/count", method = RequestMethod.GET)
    public long countsecure1(@RequestParam("name") String name) {
        name = name.replaceAll("'", "\\\\\\\\'").replaceAll("\"", "\\\\\\\\\"");
        DBObject query = (DBObject) JSON.parse("{$where: \"this.name == '" + name + "'\"}");
        long count = readMongoReadTemplate.getCollection("users").count(query);
        return count;
    }


    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    public static void main(String[] args) throws UnknownHostException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        //MongoWriteRepository writeRepository = ctx.getBean(MongoWriteRepository.class);
//        User user1 = new User("John", "1");
//        User user2 = new User("Mary", "2");
//        writeRepository.save(user1);
//        writeRepository.save(user2);
//        MongoReadRepository readRepository = ctx.getBean(MongoReadRepository.class);
//        System.out.println(readRepository.countName("John;var date=new Date(); do{curDate = new Date();}while(curDate- date<10000);"));
    }
}
