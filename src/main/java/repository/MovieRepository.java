package repository;

import model.Movie;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Long, Movie> {

    Movie save(Movie movie);

    Movie findById(Long id);
}
