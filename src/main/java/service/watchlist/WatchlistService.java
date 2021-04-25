package service.watchlist;

import model.Movie;
import model.MovieCompressed;
import model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MovieRepository;
import repository.WatchlistRepository;
import utils.exceptions.NoSuchMovieException;
import utils.exceptions.WatchlistEntryNotFoundException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteMovie(Long movieId, String username){
        Optional<Watchlist> watchlistOptional = watchlistRepository.findFirstByUsername(username);
        if(!watchlistOptional.isPresent()){
            throw new WatchlistEntryNotFoundException();
        }
        Watchlist watchlist = watchlistOptional.orElse(null);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(!movieOptional.isPresent()){
            throw new WatchlistEntryNotFoundException();
        }

        Movie movie = movieOptional.orElse(null);
        if(!watchlist.getMovies().contains(movie)){
            throw new WatchlistEntryNotFoundException();
        }
        watchlist.removeMovie(movie);
        watchlistRepository.save(watchlist);
    }

    @Transactional
    public List<MovieCompressed> getWatchlistForUser(String username){
        Optional<Watchlist> watchlistOptional = watchlistRepository.findFirstByUsername(username);
        if(!watchlistOptional.isPresent())
            return Collections.emptyList();
        Watchlist watchlist = watchlistOptional.orElse(null);
        Set<Movie> movies = watchlist.getMovies();
        return movies.stream().map(movie -> new MovieCompressed(movie)).collect(Collectors.toList());
    }

    @Transactional
    public boolean hasMovieInWatchlist(Long movieId, String username){
        Optional<Watchlist> watchlistOptional = watchlistRepository.findFirstByUsername(username);
        if(!watchlistOptional.isPresent())
            return false;
        Watchlist watchlist = watchlistOptional.orElse(null);
        Set<Movie> movies = watchlist.getMovies();
        return movies.stream().filter(movie -> movie.getId().equals(movieId)).findFirst().isPresent();
    }

}
