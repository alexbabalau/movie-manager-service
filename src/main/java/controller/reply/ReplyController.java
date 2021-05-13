package controller.reply;

import model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.reply.ReplyService;
import utils.exceptions.NoDeletePermissionException;
import utils.exceptions.NoSuchReplyException;
import utils.exceptions.NoSuchReviewException;
import utils.security.Allowed;

@RestController
@RequestMapping("/replies")
@CrossOrigin
public class ReplyController {

    private ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    @PostMapping("/{reviewId}/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Allowed
    public @ResponseBody Reply addReply(@PathVariable Long reviewId, @PathVariable String username, @RequestBody Reply reply, @RequestHeader("authorization") String token){
        try{
            return replyService.addReply(reviewId, reply, username);
        }
        catch (NoSuchReviewException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }

    @DeleteMapping("/{replyId}/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Allowed
    public void deleteReply(@PathVariable Long replyId, @PathVariable String username, @RequestHeader("authorization") String token){
        try{
            replyService.deleteReply(replyId, username);
        }
        catch(NoSuchReplyException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reply not found");
        }
        catch (NoDeletePermissionException ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Reply deletion not permitted");
        }
    }

}

