package repository;

import model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByTmdbId(Integer tmdbId);
    List<Genre> findByNameIn(List<String> names);
}
