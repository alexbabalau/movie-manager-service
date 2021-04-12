package repository;

import model.Genre;
import model.Movie;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Optional<Movie> findFirstByOrderById();
    Optional<Movie> findByTmdbId(Integer tmdbId);
    List<Movie> findByGenresIn(List<Genre> genres);
    @Query("SELECT m FROM Movie m WHERE m.id IN (select m.id from Movie m join m.genres g where g in :genres group by m.id having count(m.id) = :size)")
    Page<Movie> findByGenresContaining(List<Genre> genres, Long size, Pageable pageable);
}
