package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieCompressed {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("release_date")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;

    @JsonProperty("imagePath")
    private String imagePath;

    public MovieCompressed(Movie movie){
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.releaseDate = movie.getReleaseDate();
        this.imagePath = movie.getImagePath();
    }

    public static List<MovieCompressed> compressedMovieListFromMovies(List<Movie> movies){
        return movies.stream().map(MovieCompressed::new).collect(Collectors.toList());
    }
}
