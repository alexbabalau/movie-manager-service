package controller.search;

import model.Movie;
import model.MovieCompressed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.search.SearchService;
import utils.security.Allowed;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/title")
    public List<MovieCompressed> getMoviesByTitle(@RequestParam(required = false, defaultValue = "") String title, @RequestParam Integer page){
        return searchService.searchMoviesByTitle(title, page);
    }

    @GetMapping("/actor")
    public List<MovieCompressed> getMoviesByActor(@RequestParam(required = false, defaultValue = "") String actor, @RequestParam Integer page){
        return searchService.searchMoviesByActor(actor, page);
    }
}
