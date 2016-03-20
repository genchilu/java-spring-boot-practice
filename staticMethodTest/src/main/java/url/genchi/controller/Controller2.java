package url.genchi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test Web api with static method and static parameter
 *
 */

@RestController
public class Controller2 {
	static String myname = "";
    @RequestMapping("/test2")
    static public String test(@RequestParam(value="name", required=false, defaultValue="World") String name) throws InterruptedException {
    	myname = name;
        Thread.sleep(1000);
        return "hi, " + myname + "\n";
    }
}
