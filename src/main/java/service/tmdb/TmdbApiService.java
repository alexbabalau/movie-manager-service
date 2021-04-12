package service.tmdb;

import model.tmbd.CreditsResponse;
import model.tmbd.DiscoverResponse;
import model.tmbd.GenresResponse;
import model.tmbd.TmdbGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("tmdbApiService")
@PropertySource("classpath:/app-keys.properties")
public class TmdbApiService {

    private RestTemplate restTemplate;

    @Value("${tmdb.key}")
    private String apiKey;

    @Autowired
    public TmdbApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public DiscoverResponse getMoviesByPageAndYear(int page, int year){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&page=" + page + "&year=" + year;

        return restTemplate.exchange(url, HttpMethod.GET, entity, DiscoverResponse.class).getBody();
    }

    public CreditsResponse getCreditsForMovie(int movieTmdbId){
        String url = "https://api.themoviedb.org/3/movie/{movieId}/credits?api_key=" + apiKey;
        return restTemplate.getForObject(url, CreditsResponse.class, movieTmdbId);
    }

    public GenresResponse getGenres(){
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + apiKey;
        return restTemplate.getForObject(url, GenresResponse.class);
    }

}
