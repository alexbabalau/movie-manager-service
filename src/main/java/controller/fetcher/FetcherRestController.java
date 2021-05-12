package controller.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.fetcher.MovieFetcher;
import utils.security.Allowed;

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
    @Allowed
    public void fetchMovies(@PathVariable Integer year, @PathVariable Integer limit, @RequestHeader("AuthorizationToken") String token){
        movieFetcher.saveMoviesByYear(year, limit);
    }

    @GetMapping("/genres")
    @ResponseStatus(HttpStatus.CREATED)
    @Allowed
    public void fetchGenres(@RequestHeader("AuthorizationToken") String token){
        movieFetcher.saveGenres();
    }

}
