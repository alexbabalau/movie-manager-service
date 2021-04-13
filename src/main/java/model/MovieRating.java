package model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE_RATING")
@Immutable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRating {

    @Id
    private Long id;

    private Double rating;

}
