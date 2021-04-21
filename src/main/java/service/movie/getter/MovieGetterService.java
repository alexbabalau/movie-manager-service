package service.movie.getter;

import model.Movie;
import model.MovieCompressed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.MovieRepository;
import utils.GenreListRetriever;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service("movieGetter")
@PropertySource("classpath:/app-response.properties")
public class MovieGetterService {

    @Value("${response.items.per.page}")
    private Integer itemsPerPage;

    private Logger logger = LoggerFactory.getLogger(MovieGetterService.class);

    private MovieRepository movieRepository;
    private GenreListRetriever genreListRetriever;

    @Autowired
    public MovieGetterService(MovieRepository movieRepository, GenreListRetriever genreListRetriever){
        this.movieRepository = movieRepository;
        this.genreListRetriever = genreListRetriever;
    }

    private List<MovieCompressed> compressedMovieList(List<Movie> movies){
        return movies.stream().map(MovieCompressed::new).collect(Collectors.toList());
    }

    @Transactional
    public List<MovieCompressed> getMoviesSortedByDate(Integer page, String genresListString, String minRatingString){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Order.desc("releaseDate").nullsLast()));
        return getMovieCompressedListFromGenresAndMinRatingAndPageable(genresListString, minRatingString, pageable);
    }

    private List<MovieCompressed> getMovieCompressedListFromGenresAndMinRatingAndPageable(String genresListString, String minRatingString, Pageable pageable) {
        if(minRatingString.isEmpty())
            minRatingString = "0";
        Double minRating = Double.parseDouble(minRatingString);
        if(genresListString.isEmpty())
            return compressedMovieList(movieRepository.findByRatingGreaterThan(minRating, pageable).toList());
        List<String> genres = genreListRetriever.getGenresFromString(genresListString);
        logger.info("Min Rating used: " + String.valueOf(minRating));
        return compressedMovieList(movieRepository.findByGenresContainingAndRatingGreaterThan(genres,  (long) genres.size(), minRating, pageable).toList());
    }

    @Transactional
    public List<MovieCompressed> getMoviesSortedByTitle(Integer page, String genresListString, String minRatingString){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Order.asc("title").nullsLast()));
        return getMovieCompressedListFromGenresAndMinRatingAndPageable(genresListString, minRatingString, pageable);
    }

    @Transactional
    public Movie getMovieDetails(Long movieId){
        return movieRepository.findById(movieId).orElse(null);
    }

    @Transactional
    public List<MovieCompressed> getMoviesSortedByRating(Integer page, String genresListString, String minRatingString){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Order.desc("movieRating.rating").nullsLast()));
        return getMovieCompressedListFromGenresAndMinRatingAndPageable(genresListString, minRatingString, pageable);
    }

}
