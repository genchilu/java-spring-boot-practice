package url.genchi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test Web api with non static method and non static parameter
 *
 */
@RestController
public class Controller3 {
	private String myname = "";
    @RequestMapping("/test3")
    public String test(@RequestParam(value="name", required=false, defaultValue="World") String name) throws InterruptedException {
    	this.myname = name;
        Thread.sleep(1000);
        return "hi, " + myname + "\n";
    }
}
