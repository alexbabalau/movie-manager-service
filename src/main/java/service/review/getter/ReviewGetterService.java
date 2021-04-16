package service.review.getter;

import model.Like;
import model.Movie;
import model.MovieRating;
import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LikeRepository;
import repository.MovieRepository;
import repository.ReviewRepository;
import utils.exceptions.NoSuchMovieException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("reviewGetter")
public class ReviewGetterService {

    private MovieRepository movieRepository;
    private LikeRepository likeRepository;

    @Autowired
    public ReviewGetterService(MovieRepository movieRepository, LikeRepository likeRepository){
        this.movieRepository = movieRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public List<Review> getReviewsFromMovie(Long movieId){
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(!movieOptional.isPresent())
            throw new NoSuchMovieException();
        Movie movie = movieOptional.orElse(null);
        return new ArrayList<Review>(movie.getReviews());
    }

    @Transactional
    public List<Long> getReviewsIdFromMovieAndUsername(Long movieId, String username){
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(!movieOptional.isPresent())
            throw new NoSuchMovieException();
        return likeRepository.findLikesByMovieIdAndUsername(movieId, username).stream().map(Like::getReview).map(Review::getId).collect(Collectors.toList());
    }

}
