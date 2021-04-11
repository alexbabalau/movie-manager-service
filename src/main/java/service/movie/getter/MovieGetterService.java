package service.movie.getter;

import model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.MovieRepository;

import java.util.List;

@Service("movieGetter")
@PropertySource("classpath:/app-response.properties")
public class MovieGetterService {

    @Value("${response.items.per.page}")
    private Integer itemsPerPage;

    private MovieRepository movieRepository;

    @Autowired
    public MovieGetterService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMoviesSortedByDate(Integer page){
        //System.out.println(page);
        System.out.println(itemsPerPage);
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Direction.DESC, "releaseDate"));
        return movieRepository.findAll(pageable).toList();
    }

}
