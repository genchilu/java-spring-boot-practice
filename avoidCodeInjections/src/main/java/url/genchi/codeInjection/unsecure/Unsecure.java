package url.genchi.codeInjection.unsecure;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.validation.Valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import url.genchi.data.Formula;
import url.genchi.data.Result;

@SpringBootApplication
@RestController
public class Unsecure extends WebMvcConfigurerAdapter {
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");
    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public Result calc(@Valid Formula formula) throws ScriptException {
        Object result = engine.eval(formula.getFormula());
        //It would be dangerous while formula is a piece of malicious code like "exit()"
        return new Result(result.toString());
    }

    public static void main(String[] args) {
        SpringApplication.run(Unsecure.class, args);
    }
}
