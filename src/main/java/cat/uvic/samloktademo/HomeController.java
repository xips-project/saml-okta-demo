package cat.uvic.samloktademo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;



@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("emailAddress", principal.getFirstAttribute("email"));
        model.addAttribute("userAttributes", principal.getAttributes());
        return "home";
    }

    @RequestMapping("/getJwt")
    public String getJwt(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/auth/login";
        String requestJson = "{\"username\":\"test\",\"password\":\"test\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        HttpEntity<String> answer = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        HttpHeaders responseHeaders = answer.getHeaders();
        String cookie = responseHeaders.getFirst(HttpHeaders.SET_COOKIE);
        System.out.println(cookie);


        model.addAttribute("jwtToken",cookie);

        return "home";
    }
}
