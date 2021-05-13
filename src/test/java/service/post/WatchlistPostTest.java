package service.post;

import application.MovieManagerServiceApplication;
import model.Movie;
import model.Watchlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import repository.WatchlistRepository;
import service.watchlist.WatchlistService;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class WatchlistPostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Test
    @Transactional
    public void addMovieToWatchlistTest_StatusCreated() throws Exception {
        mockMvc.perform(post("/watchlist/20/user2")
                .header("authorization", "mockToken"))
                .andExpect(status().isCreated());
        Optional<Watchlist> watchlistOptional = watchlistRepository.findFirstByUsername("user2");
        Watchlist watchlist = watchlistOptional.orElse(null);
        assertNotNull(watchlist);
        Set<Movie> movies = watchlist.getMovies();
        assertNotNull(movies);
        assertEquals(1, movies.size());
        //assertEquals(Optional.of(20L), (movies.stream().findFirst().orElse(null)).getId());
    }

    @Test
    @Transactional
    public void addMovieToWatchlistTest_StatusNotFound() throws Exception {
        mockMvc.perform(post("/watchlist/21/user2")
                .header("authorization", "mockToken"))
                .andExpect(status().isNotFound());
    }
}
