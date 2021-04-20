package service.search;

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

import java.util.List;
import java.util.stream.Collectors;

@Service("searchService")
@PropertySource("classpath:app-response.properties")
public class SearchService {

    @Value("${response.items.per.page}")
    private Integer itemsPerPage;

    private MovieRepository movieRepository;

    @Autowired
    public SearchService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<MovieCompressed> searchMoviesByTitle(String searchedTitle, Integer page){
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by("movieRating.rating"));
        return movieRepository.findByTitleContainingIgnoreCase(searchedTitle, pageable).toList().stream().map(MovieCompressed::new).collect(Collectors.toList());
    }

}
