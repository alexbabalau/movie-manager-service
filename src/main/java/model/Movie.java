package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.tmbd.MovieCompressed;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private Integer tmdbId;

    private String title;

    private String description;

    private Date releaseDate;

    private String imagePath;

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
        this.imagePath = movieCompressed.getPosterPath();
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
