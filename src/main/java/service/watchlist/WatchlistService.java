package service.watchlist;

import model.Movie;
import model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MovieRepository;
import repository.WatchlistRepository;
import utils.exceptions.NoSuchMovieException;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service("watchlistService")
public class WatchlistService {

    private WatchlistRepository watchlistRepository;
    private MovieRepository movieRepository;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository, MovieRepository movieRepository){
        this.watchlistRepository = watchlistRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public void addMovie(Long movieId, String username){
        Optional<Watchlist> watchlistOptional = watchlistRepository.findFirstByUsername(username);
        Watchlist watchlist = watchlistOptional.orElse(new Watchlist(username));

        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(!movieOptional.isPresent()){
            throw new NoSuchMovieException();
        }

        watchlist.addMovie(movieOptional.orElse(null));

        watchlistRepository.save(watchlist);
    }

}