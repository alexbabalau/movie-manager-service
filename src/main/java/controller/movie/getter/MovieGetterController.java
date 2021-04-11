package controller.movie.getter;

import model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Movie> getNewestMoviesByPage(@PathVariable Integer page){
        return movieGetterService.getMoviesSortedByDate(page);
    }
}
