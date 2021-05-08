package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieForRecommenderService {

    private String title;

    private String description;

    private String genres;

    public static MovieForRecommenderService getMovieForRecommendedServiceFromMovie(Movie movie){
        String title = movie.getTitle();
        String description = movie.getDescription();
        String genres = movie.getGenres().stream().map(Genre::getName).collect(Collectors.joining(","));
        return new MovieForRecommenderService(title, description, genres);
    }

}
