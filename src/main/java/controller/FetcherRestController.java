package controller;

import model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.MovieFetcher;

@RestController
public class FetcherRestController {

    private MovieFetcher movieFetcher;

    @Autowired
    public FetcherRestController(MovieFetcher movieFetcher){
        this.movieFetcher = movieFetcher;
    }

    @GetMapping("/fetch/movies/{year}/{limit}")
    @ResponseStatus(HttpStatus.CREATED)
    public void fetchMovies(@PathVariable Integer year, @PathVariable Integer limit){
        movieFetcher.saveMoviesByYear(year, limit);
    }

    @GetMapping("/fetch/genres")
    @ResponseStatus(HttpStatus.CREATED)
    public void fetchGenres(){
        movieFetcher.saveGenres();
    }

}
