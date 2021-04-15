package controller.movie.getter;

import model.Movie;
import model.MovieCompressed;
import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.movie.getter.MovieGetterService;
import service.review.getter.ReviewGetterService;
import utils.exceptions.NoSuchGenreException;
import utils.exceptions.NoSuchMovieException;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieGetterController {

    private MovieGetterService movieGetterService;
    private ReviewGetterService reviewGetterService;

    @Autowired
    public MovieGetterController(MovieGetterService movieGetterService, ReviewGetterService reviewGetterService){
        this.movieGetterService = movieGetterService;
        this.reviewGetterService = reviewGetterService;
    }

    @GetMapping("/newest/{page}")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieCompressed> getNewestMoviesByPage(@PathVariable Integer page, @RequestParam(required = false, defaultValue = "") String genres){
        try{
            return movieGetterService.getMoviesSortedByDate(page, genres);
        }
        catch(NoSuchGenreException ex){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid genre", ex);
        }
    }

    @GetMapping("/topRated/{page}")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieCompressed> getTopRatedMoviesByPage(@PathVariable Integer page, @RequestParam(required = false, defaultValue = "") String genres){
        try{
            return movieGetterService.getMoviesSortedByRating(page, genres);
        }
        catch(NoSuchGenreException ex){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid genre", ex);
        }
    }

    @GetMapping("/byTitle/{page}")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieCompressed> getByTitleMoviesByPage(@PathVariable Integer page, @RequestParam(required = false, defaultValue = "") String genres){
        try{
            return movieGetterService.getMoviesSortedByTitle(page, genres);
        }
        catch(NoSuchGenreException ex){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid genre", ex);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie getMovieById(@PathVariable Long id){
        Movie movie = movieGetterService.getMovieDetails(id);
        if(movie == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        return movie;
    }

    @GetMapping("/{movieId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getReviewsByMovieId(@PathVariable Long movieId){
        try{
            return reviewGetterService.getReviewsFromMovie(movieId);
        }
        catch (NoSuchMovieException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @GetMapping("/{movieId}/likes/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getLikeReviewsIdByMovieIdAndUsername(@PathVariable Long movieId, @PathVariable String username){
        try{
            return reviewGetterService.getReviewsIdFromMovieAndUsername(movieId, username);
        }
        catch (NoSuchMovieException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

}
