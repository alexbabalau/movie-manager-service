package service.email;

import model.Email;
import model.EmailWithNoAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.exceptions.SendEmailFailedException;

@Service("emailService")
@PropertySource("classpath:app-urls.properties")
public class EmailService {

    @Value("${email.service.url}")
    private String emailServiceUrl;

    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    public EmailService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public void sendEmailToUser(String username, EmailWithNoAddress email){
        /*ResponseEntity<Object> response = restTemplate.postForEntity(emailServiceUrl + "/api/mail/" + username, email, Object.class);
        if(response.getStatusCode().isError()){
            logger.error("Error at sending email to username "+ username + "; response: " + response);
        }*/
        logger.info("Email sent to " + username);
    }

    public void sendNewsletter(EmailWithNoAddress email){
        /*ResponseEntity<Object> response = restTemplate.postForEntity(emailServiceUrl + "/api/mail/newsletter", email, Object.class);
        if(response.getStatusCode().isError()){
            logger.error("Error at sending newsletter; response: " + response);
        }*/
        logger.info("Newsletter sent");
    }

}
