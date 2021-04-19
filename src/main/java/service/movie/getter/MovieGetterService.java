package service.movie.getter;

import model.Genre;
import model.Movie;
import model.MovieCompressed;
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
    public List<MovieCompressed> getMoviesSortedByDate(Integer page, String genresListString){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Order.desc("releaseDate").nullsLast()));
        return getMovieCompressedListFromGenresAndPageable(genresListString, pageable);
    }

    private List<MovieCompressed> getMovieCompressedListFromGenresAndPageable(String genresListString, Pageable pageable) {
        if(genresListString.isEmpty())
            return compressedMovieList(movieRepository.findAll(pageable).toList());
        List<Genre> genres = genreListRetriever.getGenresFromString(genresListString);

        return compressedMovieList(movieRepository.findByGenresContaining(genres,  (long) genres.size(), pageable).toList());
    }

    @Transactional
    public List<MovieCompressed> getMoviesSortedByTitle(Integer page, String genresListString){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Order.asc("title").nullsLast()));
        return getMovieCompressedListFromGenresAndPageable(genresListString, pageable);
    }

    @Transactional
    public Movie getMovieDetails(Long movieId){
        return movieRepository.findById(movieId).orElse(null);
    }

    @Transactional
    public List<MovieCompressed> getMoviesSortedByRating(Integer page, String genresListString){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Order.desc("movieRating.rating").nullsLast()));
        return getMovieCompressedListFromGenresAndPageable(genresListString, pageable);
    }

}
