package url.genchi.shellInjection.unsecure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.script.ScriptException;
import javax.validation.Valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import url.genchi.data.DomainName;
import url.genchi.data.Result;

@SpringBootApplication
@RestController
public class Unsecure extends WebMvcConfigurerAdapter {
    @RequestMapping(value = "/host", method = RequestMethod.POST)
    public Result calc(@Valid DomainName domainName) throws ScriptException, IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", "host " + domainName.getDomainName()});
        p.waitFor();
        BufferedReader reader = 
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine())!= null) {
            sb.append(line + "\n");
        }
        return new Result(sb.toString());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(Unsecure.class, args);
    }
}
