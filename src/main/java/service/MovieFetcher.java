package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MovieRepository;

@Service
public class MovieFetcher {

    private MovieRepository movieRepository;

    @Autowired
    public MovieFetcher(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public void saveMoviesByYear(int year){

    }
}
