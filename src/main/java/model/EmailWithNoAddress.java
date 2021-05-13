package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailWithNoAddress {

    private String subject;
    private String body;

    public static EmailWithNoAddress getEmailFromReply(Reply reply, String movieTitle){
        String subject = "Your review got a reply";
        String body = "Your review from the movie " + movieTitle + " got the following reply from the user " + reply.getUsername() + ": \n" +
                "\" " + reply.getContent() + " \"\n";
        return new EmailWithNoAddress(subject, body);
    }

}
