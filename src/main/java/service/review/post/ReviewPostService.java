package service.review.post;

import model.Movie;
import model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import repository.MovieRepository;
import repository.ReviewRepository;
import utils.exceptions.NoAddPermissionException;
import utils.exceptions.NoSuchMovieException;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service("reviewPost")
public class ReviewPostService {

    private ReviewRepository reviewRepository;
    private MovieRepository movieRepository;

    private Logger logger = LoggerFactory.getLogger(ReviewPostService.class);

    @Autowired
    public ReviewPostService(ReviewRepository reviewRepository, MovieRepository movieRepository){
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public Review addReview(Review review, Long movieId){
        checkIfAlreadyExistingReview(review, movieId);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(!movieOptional.isPresent())
            throw new NoSuchMovieException();
        Movie movie = movieOptional.orElse(null);
        movie.addReview(review);
        movie = movieRepository.save(movie);
        return movie.getReviews().stream().filter(rev -> rev.getUsername().equals(review.getUsername())).findFirst().orElse(null);
    }

    private void checkIfAlreadyExistingReview(Review review, Long movieId) {
        List<Review> userReviewsToMovie = reviewRepository.getReviewByUsernameAndMovie(review.getUsername(), movieId);
        if(!userReviewsToMovie.isEmpty()){
            throw new NoAddPermissionException();
        }
    }

}
