package repository;

import model.Movie;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Optional<Movie> findFirstByOrderById();
    Optional<Movie> findByTmdbId(Integer tmdbId);
}
