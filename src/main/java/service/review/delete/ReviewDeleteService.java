package service.review.delete;

import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReviewRepository;
import utils.exceptions.NoDeletePermissionException;
import utils.exceptions.NoSuchReviewException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("reviewDelete")
public class ReviewDeleteService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewDeleteService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void deleteReview(Long reviewId, String username){
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(!reviewOptional.isPresent()){
            throw new NoSuchReviewException();
        }
        Review review = reviewOptional.orElse(null);
        if(!username.equals(review.getUsername())){
            throw new NoDeletePermissionException();
        }

        reviewRepository.deleteById(reviewId);

    }

}
