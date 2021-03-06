package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.tmbd.MovieCompressed;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "Movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private Integer tmdbId;

    private String title;

    @JsonIgnore
    private Date addedDate;

    @Lob
    private String description;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;

    private String imagePath;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private MovieRating movieRating;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movie")
    private Set<Review> reviews = new HashSet<>();

    public Movie(MovieCompressed movieCompressed){
        this.imagePath = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + movieCompressed.getPosterPath();
        this.tmdbId = movieCompressed.getId();
        this.title = movieCompressed.getTitle();
        this.description = movieCompressed.getOverview();
        this.releaseDate = movieCompressed.getReleaseDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return tmdbId.equals(movie.tmdbId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tmdbId);
    }

    public void addActor(Actor actor){
        this.actors.add(actor);
        actor.getMovies().add(this);
    }

    public void removeActor(Actor actor){
        this.actors.remove(actor);
        actor.getMovies().remove(this);
    }

    public void addGenre(Genre genre){
        this.genres.add(genre);
        genre.getMovies().add(this);
    }

    public void removeGenre(Genre genre){
        this.genres.remove(genre);
        genre.getMovies().remove(this);
    }

    public void addReview(Review review){
        this.reviews.add(review);
        review.setMovie(this);
    }

    public void removeReview(Review review){
        this.reviews.remove(review);
        review.setMovie(null);
    }

}
