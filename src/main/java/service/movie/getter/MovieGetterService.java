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

    public List<MovieCompressed> getMoviesSortedByDate(Integer page, String genresListString){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Direction.DESC, "releaseDate"));
        if(genresListString.isEmpty())
            return compressedMovieList(movieRepository.findAll(pageable).toList());
        List<Genre> genres = genreListRetriever.getGenresFromString(genresListString);

        return compressedMovieList(movieRepository.findByGenresContaining(genres,  (long) genres.size(), pageable).toList());
    }

    public List<Movie> getMoviesSortedByTitle(Integer page){
        System.out.println(itemsPerPage);
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Direction.DESC, "releaseDate"));
        return movieRepository.findAll(pageable).toList();
    }

}
