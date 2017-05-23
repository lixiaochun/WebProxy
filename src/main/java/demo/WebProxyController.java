package demo;

import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebProxyController {

	@Value("${backendhost}")
	private String host;

	@Value("${backendport}")
	private String port;

	@Value("${useproxy}")
	private boolean useproxy;

	private RestTemplate restTemplate;

	private RestTemplate getRestTemplate() {

		if (restTemplate == null) {
			System.out.println("create restTemplate instance.");
			if (useproxy) {
				SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
				InetSocketAddress address = new InetSocketAddress(host, Integer.parseInt(port));
				Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
				requestFactory.setProxy(proxy);
				restTemplate = new RestTemplate(requestFactory);
			} else {
				restTemplate = new RestTemplate();
			}
		}
		return restTemplate;
	}

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String get(HttpServletRequest request) throws Exception {
		return getRestTemplate().getForObject(request.getRequestURL().toString(), String.class);
	}
	
	@RequestMapping(value = "/get/{url}", method = RequestMethod.GET)
    public String url(@PathVariable String url) {
		return getRestTemplate().getForObject(url, String.class);
    }

	@RequestMapping("proxy")
	public String sayProxy(HttpServletRequest request) throws Exception {
		return ("this is a simple http web proxy powered by lixiaochun.");
	}
}
