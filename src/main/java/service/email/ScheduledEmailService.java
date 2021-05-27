package service.email;

import model.Email;
import model.EmailWithNoAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import service.movie.getter.MovieGetterService;

import java.util.List;
import java.util.stream.Collectors;

@Service("scheduledEmail")
public class ScheduledEmailService {

    private EmailService emailService;
    private MovieGetterService movieGetterService;

    @Autowired
    public ScheduledEmailService(EmailService emailService, MovieGetterService movieGetterService){
        this.emailService = emailService;
        this.movieGetterService = movieGetterService;
    }

    @Scheduled(cron = "0 0 6,19 * * *")
    public void sendNewsletter(){
        List<String> newestTitles = movieGetterService.getNewlyAddedTitles(0);
        EmailWithNoAddress email = new EmailWithNoAddress("Check out this new movies!",
                "The following movies were recently added to our system!: \n" + newestTitles.stream().collect(Collectors.joining(",")));
        this.emailService.sendNewsletter(email);
    }

}
