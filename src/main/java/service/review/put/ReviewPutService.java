package service.review.put;

import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReviewRepository;
import utils.exceptions.NoEditPermissionException;
import utils.exceptions.NoSuchReviewException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("reviewPut")
public class ReviewPutService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewPutService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }


    @Transactional
    public void updateReview(Review newReview, String username, Long reviewId){
        Optional<Review> retrievedReview = reviewRepository.findById(reviewId);
        if(!retrievedReview.isPresent()){
            throw new NoSuchReviewException();
        }
        Review oldReview = retrievedReview.orElse(null);
        if(!username.equals(oldReview.getUsername())){
            throw new NoEditPermissionException();
        }
        oldReview.setComment(newReview.getComment());
        oldReview.setDate(newReview.getDate());
        oldReview.setStars(newReview.getStars());
        reviewRepository.save(oldReview);
    }

}
