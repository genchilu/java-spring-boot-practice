package url.genchi.codeInjection.secure;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import url.genchi.data.Formula;
import url.genchi.data.Result;

@SpringBootApplication
@RestController
public class Secure extends WebMvcConfigurerAdapter {
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");
    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public Result calc(@Valid Formula formula) throws ScriptException {
        if(formula.getFormula().matches("[^0-9\\-\\/\\*\\+]*")) {
            throw new IllegalArgumentException("Invalid input");
        } else {
            Object result = engine.eval(formula.getFormula());
            return new Result(result.toString());
        }
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, 
                            HttpServletResponse response) throws IOException {    
          response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    public static void main(String[] args) {
        SpringApplication.run(Secure.class, args);
    }
}
