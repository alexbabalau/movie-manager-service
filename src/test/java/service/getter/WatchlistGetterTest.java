package service.getter;

import application.MovieManagerServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureRestDocs(outputDir = "target/snippets")
@ActiveProfiles("test")
public class WatchlistGetterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getWatchlistForUser_StatusOk() throws Exception {
        mockMvc.perform(get("/watchlist/user1")
                .header("authorization", "mockToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(20)))
                .andDo(document("watchlist"));
    }

    @Test
    public void hasMovieInWatchlistTestTrue_StatusOk() throws Exception{
        mockMvc.perform(get("/watchlist/exists/20/user1")
                .header("authorization", "mockToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void hasMovieInWatchlistTestFalse_StatusOk() throws Exception{
        mockMvc.perform(get("/watchlist/exists/21/user1")
                .header("authorization", "mockToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
