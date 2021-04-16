package controller.reviews;

import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.review.post.ReviewPostService;
import service.review.put.ReviewPutService;
import utils.exceptions.NoAddPermissionException;
import utils.exceptions.NoEditPermissionException;
import utils.exceptions.NoSuchMovieException;
import utils.exceptions.NoSuchReviewException;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    private ReviewPostService reviewPostService;
    private ReviewPutService reviewPutService;

    @Autowired
    public ReviewsController(ReviewPostService reviewPostService, ReviewPutService reviewPutService){
        this.reviewPostService = reviewPostService;
        this.reviewPutService = reviewPutService;
    }

    @PostMapping("/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Review addReview(@PathVariable Long movieId, @RequestBody Review review){
        try{
            return reviewPostService.addReview(review, movieId);
        }
        catch (NoSuchMovieException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        catch(NoAddPermissionException ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username already has a review");
        }
    }

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReview(@PathVariable String username, @RequestBody Review newReview){
        try{
            reviewPutService.updateReview(newReview, username);
        }
        catch (NoSuchReviewException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        catch (NoEditPermissionException ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No edit permission for this review");
        }
    }
}
