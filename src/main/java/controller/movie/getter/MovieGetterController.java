package controller.movie.getter;

import model.Movie;
import model.MovieCompressed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.movie.getter.MovieGetterService;
import utils.exceptions.NoSuchGenreException;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieGetterController {

    private MovieGetterService movieGetterService;

    @Autowired
    public MovieGetterController(MovieGetterService movieGetterService){
        this.movieGetterService = movieGetterService;
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

}
