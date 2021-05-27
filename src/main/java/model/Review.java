package model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stars;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    @Lob
    private String comment;

    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "review")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "review")
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return username.equals(review.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public void addReply(Reply reply){
        this.replies.add(reply);
        reply.setReview(this);
    }

    public void removeReply(Reply reply){
        this.replies.remove(reply);
        reply.setReview(null);
    }

    public void addLike(Like like){
        this.likes.add(like);
        like.setReview(this);
    }

    public void removeLike(Like like){
        this.likes.remove(like);
        like.setReview(null);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", stars=" + stars +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                ", username='" + username + '\'' +
                "id: " + movie.getId() +
                '}';
    }
}
