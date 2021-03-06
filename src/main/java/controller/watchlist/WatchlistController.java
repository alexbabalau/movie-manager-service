package controller.watchlist;

import model.MovieCompressed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.watchlist.WatchlistService;
import utils.exceptions.NoSuchMovieException;
import utils.exceptions.WatchlistEntryNotFoundException;
import utils.security.Allowed;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
@CrossOrigin
public class WatchlistController {

    private WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService){
        this.watchlistService = watchlistService;
    }

    @GetMapping("/exists/{movieId}/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Allowed
    public @ResponseBody  Boolean hasMovieInWatchlist(@PathVariable Long movieId, @PathVariable String username, @RequestHeader("authorization") String token){
        return watchlistService.hasMovieInWatchlist(movieId, username);
    }

    @PostMapping("/{movieId}/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    @Allowed
    public void addMovieToWatchlist(@PathVariable Long movieId, @PathVariable String username, @RequestHeader("authorization") String token){
        try{
            watchlistService.addMovie(movieId, username);
        }
        catch(NoSuchMovieException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @DeleteMapping("/{movieId}/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Allowed
    public void removeMovieFromWatchlist(@PathVariable Long movieId, @PathVariable String username, @RequestHeader("authorization") String token){
        try{
            watchlistService.deleteMovie(movieId, username);
        }
        catch(WatchlistEntryNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Watchlist entry not found");
        }
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Allowed
    public List<MovieCompressed> getUserWatchlist(@PathVariable String username, @RequestHeader("authorization") String token){
        return watchlistService.getWatchlistForUser(username);
    }

}
