package url.genchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@SpringBootApplication
@EnableAsync
public class Application {

    @Autowired
    AsyncService asyncService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String asyncFun() throws Exception{
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<String> page1 = asyncService.findUser("PivotalSoftware");
        Future<String> page2 = asyncService.findUser("CloudFoundry");
        Future<String> page3 = asyncService.findUser("Spring-Projects");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
        System.out.println(page1.get());
        System.out.println(page2.get());
        System.out.println(page3.get());
        return "total time: " + Long.toString(System.currentTimeMillis() - start) + "ms";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
