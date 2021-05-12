package controller.reviews;

import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.review.delete.ReviewDeleteService;
import service.review.post.ReviewPostService;
import service.review.put.ReviewPutService;
import utils.exceptions.*;
import utils.security.Allowed;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
public class ReviewsController {

    private ReviewPostService reviewPostService;
    private ReviewPutService reviewPutService;
    private ReviewDeleteService reviewDeleteService;

    @Autowired
    public ReviewsController(ReviewPostService reviewPostService, ReviewPutService reviewPutService, ReviewDeleteService reviewDeleteService){
        this.reviewPostService = reviewPostService;
        this.reviewPutService = reviewPutService;
        this.reviewDeleteService = reviewDeleteService;
    }

    @PostMapping("/{movieId}/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Allowed
    public @ResponseBody Review addReview(@PathVariable Long movieId, @RequestBody Review review, @PathVariable String username, @RequestHeader("AuthorizationToken") String token){
        try{
            return reviewPostService.addReview(review, movieId, username);
        }
        catch (NoSuchMovieException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        catch(NoAddPermissionException ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username already has a review");
        }
    }

    @PutMapping("/{reviewId}/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Allowed
    public void updateReview(@PathVariable String username, @RequestBody Review newReview, @PathVariable Long reviewId, @RequestHeader("AuthorizationToken") String token){
        try{
            reviewPutService.updateReview(newReview, username, reviewId);
        }
        catch (NoSuchReviewException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        catch (NoEditPermissionException ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No edit permission for this review");
        }
    }

    @DeleteMapping("/{reviewId}/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Allowed
    public void deleteReview(@PathVariable String username, @PathVariable Long reviewId, @RequestHeader("AuthorizationToken") String token){
        try{
            reviewDeleteService.deleteReview(reviewId, username);
        }
        catch(NoSuchReviewException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        catch(NoDeletePermissionException ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No delete permission for this review");
        }
    }
}
