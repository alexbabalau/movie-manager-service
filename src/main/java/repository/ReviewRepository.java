package repository;

import model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select r from Review r where r.username = :username and r.movie.id = :id order by r.date")
    List<Review> getReviewByUsernameAndMovie(String username, Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "delete from Review r where r.id = :id")
    int deleteReviewById(Long id);

    @Query(value = "select r from Review r where r.username = :username and r.stars >= :minStars")
    List<Review> getReviewByUsernameWithMinStars(String username, Integer minStars, Pageable pageable);
}
