package controller.search;

import model.Movie;
import model.MovieCompressed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.search.SearchService;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/{title}/{page}")
    public List<MovieCompressed> getMoviesByTitle(@PathVariable String title, @PathVariable Integer page){
        return searchService.searchMoviesByTitle(title, page);
    }

}
