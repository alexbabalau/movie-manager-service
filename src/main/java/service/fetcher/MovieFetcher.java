package service.fetcher;

import model.Actor;
import model.Genre;
import model.Movie;
import model.tmbd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ActorRepository;
import repository.GenreRepository;
import repository.MovieRepository;
import service.tmdb.TmdbApiService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("movieFetcher")
public class MovieFetcher {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private ActorRepository actorRepository;
    private TmdbApiService tmdbApiService;
    private int totalSaved = 0;

    @Autowired
    public MovieFetcher(MovieRepository movieRepository, GenreRepository genreRepository, ActorRepository actorRepository, TmdbApiService tmdbApiService){
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.tmdbApiService = tmdbApiService;

    }

    private List<Actor> getActorsFromCreditResponse(CreditsResponse creditsResponse){
        List<Actor> actors = new ArrayList<>();
        for(TmdbActor tmdbActor : creditsResponse.getCast()){
            Optional<Actor> persistedActor = actorRepository.findByTmdbId(tmdbActor.getId());
            actors.add(persistedActor.orElse(new Actor(tmdbActor)));
        }
        return actors;
    }

    private void addActorsToMovieFromMovieCompressed(Movie movie, MovieCompressed movieCompressed){
        Integer tmdbId = movieCompressed.getId();
        CreditsResponse tmdbCredits = tmdbApiService.getCreditsForMovie(tmdbId);
        List<Actor> actors = getActorsFromCreditResponse(tmdbCredits);
        for(Actor actor:actors){
            movie.addActor(actor);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveMoviesFromDiscoverResponse(DiscoverResponse response, int limit){
        List<MovieCompressed> moviesFromResponse = response.getResults();
        for(MovieCompressed movieCompressed : moviesFromResponse){
            Optional<Movie> savedMovie = movieRepository.findByTmdbId(movieCompressed.getId());
            if(savedMovie.isPresent())
                continue;
            Movie movie = new Movie(movieCompressed);
            addGenresToMovieFromMovieCompressed(movie, movieCompressed);
            addActorsToMovieFromMovieCompressed(movie, movieCompressed);
            movieRepository.save(movie);
            totalSaved += 1;
            if(totalSaved == limit)
                break;
        }
    }

    private void addGenresToMovieFromMovieCompressed(Movie movie, MovieCompressed movieCompressed) {
        Integer[] genreIds = movieCompressed.getGenreIds();
        for(Integer genreId:genreIds){
            Optional<Genre> genre = genreRepository.findByTmdbId(genreId);
            if(genre.isPresent())
                movie.addGenre(genre.get());
        }
    }

    @Transactional
    public void saveGenres() {
        List<TmdbGenre> genres = tmdbApiService.getGenres().getGenres();
        for(TmdbGenre tmdbGenre : genres){
            if(genreRepository.findByTmdbId(tmdbGenre.getId()).isPresent()){
                continue;
            }
            Genre genre = new Genre(tmdbGenre);
            genreRepository.save(genre);
        }
    }

    @Transactional
    public void saveMoviesByYear(int year, int limit){
        int currentPage = 0;
        totalSaved = 0;
        while(totalSaved < limit){
            ++currentPage;
            DiscoverResponse discoverResponse = tmdbApiService.getMoviesByPageAndYear(currentPage, year);
            saveMoviesFromDiscoverResponse(discoverResponse, limit);
        }
    }
}
