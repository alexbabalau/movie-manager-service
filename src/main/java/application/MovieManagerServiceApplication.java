package application;

import model.Movie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import service.MovieFetcher;

@SpringBootApplication(scanBasePackages = {"config"})
@EntityScan("model")
public class MovieManagerServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MovieManagerServiceApplication.class, args);
		//MovieFetcher movieFetcher = context.getBean(MovieFetcher.class);
		//movieFetcher.saveMoviesByYear(2020, 1);
	}
}
