package url.genchi.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test Web api with non static method and non static parameter
 *
 */
@Scope("prototype")
@RestController
public class Controller4 {
	private String myname = "";
    @RequestMapping("/test4")
    public String test(@RequestParam(value="name", required=false, defaultValue="World") String name) throws InterruptedException {
    	this.myname = name;
        Thread.sleep(1000);
        return "hi, " + myname + "\n";
    }
}
