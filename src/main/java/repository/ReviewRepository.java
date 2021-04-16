package repository;

import model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select r from Review r where r.username = :username and r.movie.id = :id")
    List<Review> getReviewByUsernameAndMovie(String username, Long id);
}
