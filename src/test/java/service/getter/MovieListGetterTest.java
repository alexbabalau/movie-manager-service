package service.getter;

import application.MovieManagerServiceApplication;
import model.tmbd.MovieCompressed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import repository.MovieRepository;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(
        classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class MovieListGetterTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void movieByTitleGetterTest_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/byTitle/0?genres=Action")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].title", is("Monster Hunter")))
        .andExpect(jsonPath("$[1].title", is("Mortal Kombat Legends: Scorpion's Revenge")))
                .andExpect(jsonPath("$[2].title", is("Wonder Woman 1984")));
    }

    @Test
    public void movieByTitleWithGenresAndMinRatingGetterTest_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/byTitle/0?genres=Action&minRating=2.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Monster Hunter")))
                .andExpect(jsonPath("$[1].title", is("Mortal Kombat Legends: Scorpion's Revenge")));
    }

    @Test
    public void movieByTitleGetterTest_StatusBadRequest() throws Exception {
        mockMvc.perform(get("/movies/byTitle/0?genres=Actionnn")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void movieByDateGetterTest_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/newest/0?genres=Action")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[1].title", is("Monster Hunter")))
                .andExpect(jsonPath("$[2].title", is("Mortal Kombat Legends: Scorpion's Revenge")))
                .andExpect(jsonPath("$[0].title", is("Wonder Woman 1984")));
    }

    @Test
    public void movieByDateWithGenresAndMinRatingGetterTest_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/newest/0?genres=Action&minRating=2.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("Monster Hunter")))
                .andExpect(jsonPath("$[1].title", is("Mortal Kombat Legends: Scorpion's Revenge")));
    }

    @Test
    public void movieByDateGetterTest_StatusBadRequest() throws Exception {
        mockMvc.perform(get("/movies/newest/0?genres=Actionnnn")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getMovieDetailsTest_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("Monster Hunter")))
                .andExpect(jsonPath("$.movieRating.rating", is(2.0)))
                .andExpect(jsonPath("$.id", is(20)));
    }

    @Test
    public void getMovieDetailsTest_StatusNotFound() throws Exception {
        mockMvc.perform(get("/movies/1111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void movieByRatingGetterTest_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/topRated/0?genres=Action")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[1].title", is("Monster Hunter")))
                .andExpect(jsonPath("$[0].title", is("Mortal Kombat Legends: Scorpion's Revenge")))
                .andExpect(jsonPath("$[2].title", is("Wonder Woman 1984")));
    }

    @Test
    public void movieByRatingWithGenresAndMinRatingGetterTest_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/topRated/0?genres=Action&minRating=2.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[1].title", is("Monster Hunter")))
                .andExpect(jsonPath("$[0].title", is("Mortal Kombat Legends: Scorpion's Revenge")));
    }

    @Test
    public void movieByRatingGetterTest_StatusBadRequest() throws Exception {
        mockMvc.perform(get("/movies/topRated/0?genres=Actionnnn")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void movieByRatingGetterTestWrongMinRating_StatusBadRequest() throws Exception{
        mockMvc.perform(get("/movies/topRated/0?genres=Action&minRating=alex")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
