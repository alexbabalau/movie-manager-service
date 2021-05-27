package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import service.fetcher.MovieFetcher;

@SpringBootApplication(scanBasePackages = {"config"})
@EntityScan("model")
@EnableScheduling
public class MovieManagerServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MovieManagerServiceApplication.class, args);
		//MovieFetcher movieFetcher = context.getBean(MovieFetcher.class);

		//movieFetcher.saveGenres();

		//movieFetcher.saveMoviesByYear(2020, 10);
		//int i;
		//for(i = 2019; i >= 2000; i--)
		//	movieFetcher.saveMoviesByYear(i, 50);
	}
}
