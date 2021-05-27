package model.tmbd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieCompressed {

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private Date releaseDate;

    @JsonProperty("genre_ids")
    private Integer[] genreIds;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("title")
    private String title;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("popularity")
    private String popularity;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("video")
    private boolean video;

    @JsonProperty("vote_average")
    private Double voteAverage;

    public String getReleaseDateWithFormat(DateFormat dateFormat){
        return dateFormat.format(releaseDate);
    }

    @Override
    public String toString() {
        return "MovieCompressed{" +
                "posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate=" + releaseDate +
                ", genreIds=" + Arrays.toString(genreIds) +
                ", id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", title='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity='" + popularity + '\'' +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                '}';
    }

}
