package controller.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import repository.LikeRepository;
import service.review.post.ReviewPostService;
import utils.exceptions.NoAddPermissionException;
import utils.exceptions.NoSuchReviewException;

@Controller
@RequestMapping("/likes")
public class LikesController {

    private ReviewPostService reviewPostService;

    @Autowired
    public LikesController(ReviewPostService reviewPostService){
        this.reviewPostService = reviewPostService;
    }

    @PostMapping("/{reviewId}/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLike(@PathVariable Long reviewId, @PathVariable String username){
        try{
            reviewPostService.addLikeToReview(reviewId, username);
        }
        catch(NoAddPermissionException ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Like already exists", ex);
        }
        catch(NoSuchReviewException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found", ex);
        }
    }
}
