package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.tmbd.TmdbActor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "actors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String imagePath;

    @NaturalId
    private Integer tmdbId;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    public Actor(TmdbActor tmdbActor){
        this.name = tmdbActor.getName();
        this.imagePath = tmdbActor.getProfilePath();
        this.tmdbId = tmdbActor.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return name.equals(actor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
