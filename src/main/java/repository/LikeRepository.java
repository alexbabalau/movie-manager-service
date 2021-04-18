package repository;

import model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("Select l From Like l where l.review.movie.id = :id and l.username = :username")
    List<Like> findLikesByMovieIdAndUsername(Long id, String username);

    @Query("Select l From Like l where l.review.id = :id and l.username = :username")
    List<Like> findLikesByReviewIdAnsUsername(Long id, String username);
}
