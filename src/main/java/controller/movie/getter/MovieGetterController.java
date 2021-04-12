package controller.movie.getter;

import model.Movie;
import model.MovieCompressed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.movie.getter.MovieGetterService;

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
    public List<MovieCompressed> getNewestMoviesByPage(@PathVariable Integer page, @RequestParam(required = false, defaultValue = "") String genres){
        return movieGetterService.getMoviesSortedByDate(page, genres);
    }
}
