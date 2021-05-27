package controller.likes;

import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import repository.LikeRepository;
import service.review.delete.ReviewDeleteService;
import service.review.post.ReviewPostService;
import utils.exceptions.NoAddPermissionException;
import utils.exceptions.NoSuchLikeException;
import utils.exceptions.NoSuchReviewException;
import utils.security.Allowed;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private ReviewPostService reviewPostService;
    private ReviewDeleteService reviewDeleteService;

    @Autowired
    public LikesController(ReviewPostService reviewPostService, ReviewDeleteService reviewDeleteService){
        this.reviewPostService = reviewPostService;
        this.reviewDeleteService = reviewDeleteService;
    }

    @PostMapping("/{reviewId}/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    @Allowed
    public void addLike(@PathVariable Long reviewId, @PathVariable String username, @RequestHeader("authorization") String token){
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

    @DeleteMapping("/{reviewId}/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Allowed
    public void deleteLike(@PathVariable Long reviewId, @PathVariable String username, @RequestHeader("authorization") String token){
        try{
            reviewDeleteService.deleteLike(reviewId, username);
        }
        catch(NoSuchLikeException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Like not found", ex);
        }
    }
}
