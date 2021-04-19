package controller.reply;

import model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.reply.ReplyService;
import utils.exceptions.NoSuchReviewException;

@Controller
@RequestMapping("/replies")
public class ReplyController {

    private ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    @PostMapping("/{reviewId}/{username}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Reply addReply(@PathVariable Long reviewId, @PathVariable String username, @RequestBody Reply reply){
        try{
            return replyService.addReply(reviewId, reply, username);
        }
        catch (NoSuchReviewException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }

}

