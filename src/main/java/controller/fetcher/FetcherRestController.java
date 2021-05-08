package controller.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.fetcher.MovieFetcher;

@RestController
@RequestMapping("/fetch")
@CrossOrigin
public class FetcherRestController {

    private MovieFetcher movieFetcher;

    @Autowired
    public FetcherRestController(MovieFetcher movieFetcher){
        this.movieFetcher = movieFetcher;
    }

    @GetMapping("/movies/{year}/{limit}")
    @ResponseStatus(HttpStatus.CREATED)
    public void fetchMovies(@PathVariable Integer year, @PathVariable Integer limit){
        movieFetcher.saveMoviesByYear(year, limit);
    }

    @GetMapping("/genres")
    @ResponseStatus(HttpStatus.CREATED)
    public void fetchGenres(){
        movieFetcher.saveGenres();
    }

}
