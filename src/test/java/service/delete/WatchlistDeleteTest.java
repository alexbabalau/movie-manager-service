package service.delete;

import application.MovieManagerServiceApplication;
import model.Movie;
import model.Review;
import model.Watchlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import repository.WatchlistRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class WatchlistDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Test
    @Transactional
    public void watchlistEntryDeleteTest_StatusNoContent() throws Exception{

        mockMvc.perform(delete("/watchlist/20/user1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Watchlist> retrievedWatchlist = watchlistRepository.findById(1L);
        assertNotNull(retrievedWatchlist);
        assertTrue(retrievedWatchlist.isPresent());
        Watchlist watchlist = retrievedWatchlist.orElse(null);
        Set<Movie> movies = watchlist.getMovies();

        assertEquals(0, movies.size());

    }

}
