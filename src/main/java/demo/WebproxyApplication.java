package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;

/**
 *
 *
 *  mvn clean package
 *  mvn spring-boot:run
 *
 *  cf push iot-gem-proxy -m 512m -p ./target/webproxy-0.0.1-SNAPSHOT.war
 *
 * @author mkim@pivotal.io
 */
@RestController
@EnableAutoConfiguration
//@PropertySource("classpath:application.properties")
public class WebproxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebproxyApplication.class, args);

    }
    @Value("${backendhost}")
    private String host;

    @Value("${backendport}")
    private String port;

    private RestTemplate restTemplate;

    private RestTemplate getRestTemplate(){
        if(restTemplate==null){
            System.out.println("init backend: http://"+ host +":"+port);
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            InetSocketAddress address = new InetSocketAddress(host,Integer.parseInt(port));
            Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
            requestFactory.setProxy(proxy);
            restTemplate= new RestTemplate(requestFactory);
        }
        return restTemplate;
    }

    public WebproxyApplication() throws Exception{

    }

//    @Bean
//    public RestTemplate restTemplate() throws Exception{
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        InetSocketAddress address = new InetSocketAddress(host,Integer.parseInt(port));
//        Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
//        requestFactory.setProxy(proxy);
//        return new RestTemplate(requestFactory);
//    }

    @RequestMapping( value="/**", method= RequestMethod.GET)
    public String get( HttpServletRequest request) throws Exception{
        return getRestTemplate().getForObject(request.getRequestURL().toString(), String.class);
    }
}
