package service.movie.getter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import lombok.SneakyThrows;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import repository.MovieRepository;
import repository.ReviewRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("recommenderService")
@PropertySource("classpath:app-urls.properties")
@PropertySource("classpath:app-service.properties")
public class RecommenderService {

    private MovieRepository movieRepository;
    private ReviewRepository reviewRepository;
    private MovieGetterService movieGetterService;
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${recommender.service.url}")
    private String recommenderServiceBaseUrl;

    @Value("${reviews.recommendation.number}")
    private Integer reviewsNumberForRecommendation;

    @Value("${reviews.recommendation.minstars}")
    private Integer reviewsMinStarsForRecommendation;

    @Autowired
    public RecommenderService(MovieRepository movieRepository, ReviewRepository reviewRepository, MovieGetterService movieGetterService, RestTemplate restTemplate){
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.movieGetterService = movieGetterService;
        this.restTemplate = restTemplate;
    }

    @SneakyThrows
    private List<String> getRecommendedTitles(List<MovieForRecommenderService> movies){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity< List<MovieForRecommenderService> > request =
                new HttpEntity< List<MovieForRecommenderService> >(movies, headers);

        String[] recommendation = restTemplate.postForObject(recommenderServiceBaseUrl + "/recommend", request, String[].class);

        return Arrays.asList(recommendation);
    }

    public List<MovieCompressed> recommendMovies(String username){

        Pageable pageable = PageRequest.of(0, reviewsNumberForRecommendation, Sort.by("date").descending());

        List<Review> userReviews = reviewRepository.getReviewByUsernameWithMinStars(username, reviewsMinStarsForRecommendation, pageable);

        if(userReviews.isEmpty()){
            return movieGetterService.getMoviesSortedByRating(0, "", "");
        }

        List<MovieForRecommenderService> moviesForRecommendation = userReviews.stream().map(Review::getMovie).map(MovieForRecommenderService::getMovieForRecommendedServiceFromMovie).collect(Collectors.toList());
        List<String> recommendedTitles = getRecommendedTitles(moviesForRecommendation);

        List<Movie> recommendedMovies = movieRepository.findByTitleIn(recommendedTitles);

        return MovieCompressed.compressedMovieListFromMovies(recommendedMovies);

    }

}
