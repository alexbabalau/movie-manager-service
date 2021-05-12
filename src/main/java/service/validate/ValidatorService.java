package service.validate;

import model.UsernameWithToken;
import model.ValidateApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("validatorService")
@PropertySource("classpath:app-urls.properties")
public class ValidatorService {

    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(ValidatorService.class);

    @Value("${users.service.url}")
    private String usersServiceUrl;

    @Autowired
    public ValidatorService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public boolean areTokenAndUsernameValid(String token, String username){

        UsernameWithToken usernameWithToken = new UsernameWithToken(username, token);

        HttpEntity<UsernameWithToken> request = new HttpEntity<>(usernameWithToken);

        System.out.println(restTemplate);

        ResponseEntity<ValidateApiResponse> response = restTemplate.exchange(usersServiceUrl + "/api/validate", HttpMethod.POST, request, ValidateApiResponse.class);

        logger.info("Response from validate: " + response.getStatusCode());

        return !response.getStatusCode().isError();
    }

}
