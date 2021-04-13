package service.review.getter;

import model.Movie;
import model.MovieRating;
import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MovieRepository;
import repository.ReviewRepository;
import utils.exceptions.NoSuchMovieException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("reviewGetter")
public class ReviewGetterService {

    private MovieRepository movieRepository;

    @Autowired
    public ReviewGetterService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Review> getReviewsFromMovie(Long movieId){
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(!movieOptional.isPresent())
            throw new NoSuchMovieException();
        Movie movie = movieOptional.orElse(null);
        return new ArrayList<Review>(movie.getReviews());
    }

}
