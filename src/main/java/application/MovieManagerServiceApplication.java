package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import service.fetcher.MovieFetcher;

@SpringBootApplication(scanBasePackages = {"config"})
@EntityScan("model")
public class MovieManagerServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MovieManagerServiceApplication.class, args);
		//MovieFetcher movieFetcher = context.getBean(MovieFetcher.class);
		//movieFetcher.saveGenres();
		//movieFetcher.saveMoviesByYear(2020, 24);
	}
}
