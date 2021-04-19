package service.reply;

import model.Reply;
import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReplyRepository;
import repository.ReviewRepository;
import utils.exceptions.NoDeletePermissionException;
import utils.exceptions.NoSuchReplyException;
import utils.exceptions.NoSuchReviewException;

import java.util.Optional;

@Service("replyService")
public class ReplyService {

    private ReplyRepository replyRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository, ReviewRepository reviewRepository){
        this.replyRepository = replyRepository;
        this.reviewRepository = reviewRepository;
    }

    public Reply addReply(Long reviewId, Reply reply, String username){
        reply.setUsername(username);
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(!reviewOptional.isPresent()){
            throw new NoSuchReviewException();
        }
        Review review = reviewOptional.orElse(null);
        reply = replyRepository.save(reply);
        review.addReply(reply);
        reviewRepository.save(review);
        return reply;
    }

    public void deleteReply(Long replyId, String username){
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        if(!replyOptional.isPresent()){
            throw new NoSuchReplyException();
        }
        Reply reply = replyOptional.orElse(null);
        if(!username.equals(reply.getUsername())){
            throw new NoDeletePermissionException();
        }
        replyRepository.deleteById(replyId);
    }

}