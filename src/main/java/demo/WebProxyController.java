package demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebProxyController {

	private RestTemplate restTemplate;

	private RestTemplate getRestTemplate() {

		if (restTemplate == null) {
			System.out.println("create restTemplate instance.");
			restTemplate = new RestTemplate();
		}
		return restTemplate;
	}

/*	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String get(HttpServletRequest request) throws Exception {
		return getRestTemplate().getForObject(request.getRequestURL().toString(), String.class);
	}*/
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String sayHello(HttpServletRequest request) throws Exception {
		// return ("this is a simple http web proxy powered by lixiaochun."); 
		return getRestTemplate().getForObject(request.getRequestURL().toString(), String.class);
	}
	
	@RequestMapping("proxy")
	public String sayProxy(HttpServletRequest request) throws Exception {
		 return ("this is a simple http web proxy powered by lixiaochun."); 
		// return getRestTemplate().getForObject(request.getRequestURL().toString(), String.class);
	}
}
