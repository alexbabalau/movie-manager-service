package controller.watchlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import service.watchlist.WatchlistService;
import utils.exceptions.NoSuchMovieException;

@Controller
@RequestMapping("/watchlist")
public class WatchlistController {

    private WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService){
        this.watchlistService = watchlistService;
    }

    @PostMapping("/{movieId}/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovieToWatchlist(@PathVariable Long movieId, @PathVariable String username){
        try{
            watchlistService.addMovie(movieId, username);
        }
        catch(NoSuchMovieException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

}
