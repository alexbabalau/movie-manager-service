package service.review.delete;

import model.Like;
import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LikeRepository;
import repository.ReviewRepository;
import utils.exceptions.NoAddPermissionException;
import utils.exceptions.NoDeletePermissionException;
import utils.exceptions.NoSuchLikeException;
import utils.exceptions.NoSuchReviewException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("reviewDelete")
public class ReviewDeleteService {

    private ReviewRepository reviewRepository;
    private LikeRepository likeRepository;

    @Autowired
    public ReviewDeleteService(ReviewRepository reviewRepository, LikeRepository likeRepository){
        this.reviewRepository = reviewRepository;
        this.likeRepository = likeRepository;
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

    @Transactional
    public void deleteLike(Long reviewId, String username){
        List<Like> userLikesToReview = likeRepository.findLikesByReviewIdAnsUsername(reviewId, username);

        if(userLikesToReview.isEmpty()){
            throw new NoSuchLikeException();
        }

        Long likeId = userLikesToReview.get(0).getId();

        likeRepository.deleteById(likeId);

    }

    private void checkIfNotExistingReview(Long reviewId, String username){

    }

}
