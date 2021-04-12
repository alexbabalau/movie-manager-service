package utils;

import model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.GenreRepository;
import utils.exceptions.NoSuchGenreException;

import java.util.Arrays;
import java.util.List;

@Service("genreListRetriever")
public class GenreListRetriever {

    private GenreRepository genreRepository;
    private Logger logger = LoggerFactory.getLogger(GenreListRetriever.class);

    @Autowired
    public GenreListRetriever(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    public List<Genre> getGenresFromString(String genres){
        List<String> genreList = Arrays.asList(genres.split(","));
        List<Genre> retrievedGenres = genreRepository.findByNameIn(genreList);
        if(retrievedGenres.size() != genreList.size()){
            logger.error("Retrieved Genres: " + retrievedGenres.toString() + ", Genre List: " + genreList);
            throw new NoSuchGenreException();
        }
        return retrievedGenres;
    }

}
