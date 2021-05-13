package config;

import model.EmailWithNoAddress;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import service.email.EmailService;
import service.validate.ValidatorService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;

@Profile("test")
@Configuration
public class TestConfig {

    @Bean
    @Primary
    public ValidatorService validatorServiceMock(){
        ValidatorService validatorService = Mockito.mock(ValidatorService.class);
        Mockito.when(validatorService.areTokenAndUsernameValid(anyString(), anyString())).thenReturn(true);
        return validatorService;
    }

    @Bean
    @Primary
    public EmailService emailServiceMock(){
        EmailService emailService = Mockito.mock(EmailService.class);
        doNothing().when(emailService).sendEmailToUser(isA(String.class), isA(EmailWithNoAddress.class));
        doNothing().when(emailService).sendNewsletter(isA(EmailWithNoAddress.class));
        return emailService;
    }

}
