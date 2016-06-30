package url.genchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mac on 2016/6/29.
 */
@SpringBootApplication
@RestController
@ServletComponentScan(basePackages = {"url.genchi.servlet", "url.genchi.filter"})
public class Application {

//    @Bean
//    UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
//        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
//        factory.addBuilderCustomizers(
//                builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
//        return factory;
//    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        //http2 client sample
        //RestTemplate http2Template = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        //RestTemplate http11Template = new RestTemplate();
        //String http11Response = http11Template.getForObject("https://localhost:8443/", String.class);
        //String http2Response = http2Template.getForObject("https://localhost:8443/", String.class);
        //System.out.println("HTTP/1.1 : " + http11Response.contains("You are using HTTP/2 right now!"));
        //System.out.println("HTTP/2 : " + http2Response.contains("You are using HTTP/2 right now!"));
    }
}
