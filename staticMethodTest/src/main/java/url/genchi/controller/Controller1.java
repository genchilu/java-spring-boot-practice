package url.genchi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test Web api with static method
 *
 */

@RestController
public class Controller1 {
    @RequestMapping("/test1")
    static public String test(@RequestParam(value="name", required=false, defaultValue="World") String name) throws InterruptedException {
        String localname = name;
        Thread.sleep(1000);
        return "hi, " + localname + "\n";
    }
}
