package url.genchi;

/**
 * Created by mac on 2016/6/22.
 */

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.messagequeue.rabbitmq.rpcclient.RpcClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"url.genchi.configs", "url.genchi.messagequeue.rabbitmq.rpcclient"})
public class Application {
    @Autowired
    private RpcClientService rpcClientService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("count") == null) {
            session.setAttribute("count", 0);
        }
        int count = (Integer)session.getAttribute("count") + 1;
        session.setAttribute("count", count);
        return session.getAttribute("count").toString();
    }

    @RequestMapping(value = "/bigfile", method = RequestMethod.GET)
    public void bigFile(HttpServletResponse response) throws IOException {

        String filePath = getClass().getClassLoader().getResource("bigfile.txt").getFile();
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(filePath);
        response.setContentType("utf-8");
        IOUtils.copy(fis, response.getOutputStream());
        response.flushBuffer();
        fis.close();
        response.getOutputStream().close();
    }
    @RequestMapping(value = "/rpc", method = RequestMethod.GET)
    public String rpc(@RequestParam("msg") String msg) throws Exception {
        return rpcClientService.call(msg);
        //return msg;
    }
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("id");
        serializer.setCookieMaxAge(-1);
        serializer.setUseHttpOnlyCookie(true);
        serializer.setDomainName("localhost");
        //serializer.setUseSecureCookie(true); //for https
        return serializer;
    }

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }
}
