package service;

import application.MovieManagerServiceApplication;
import model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import repository.MovieRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@EnableTransactionManagement
public class MovieFetcherTest {

    @Autowired
    private MovieFetcher movieFetcher;

    @Autowired
    private MovieRepository movieRepository;

    private static Logger logger = LoggerFactory.getLogger(MovieFetcherTest.class);

    @BeforeEach
    public void setup(){
        movieFetcher.saveGenres();
    }

    @Test
    @Transactional
    public void fetchTest(){
        movieFetcher.saveMoviesByYear(2020, 1);
        Optional<Movie> movie = movieRepository.findFirstByOrderById();
        assertTrue(movie.isPresent());
        Movie actualMovie = movie.get();
        assertNotNull(actualMovie.getTitle());
        assertTrue(actualMovie.getGenres().size() > 0);
        assertTrue(actualMovie.getActors().size() > 0);
        logger.info("Title: " + actualMovie.getTitle());

    }

}
