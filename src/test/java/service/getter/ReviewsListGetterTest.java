package service.getter;

import application.MovieManagerServiceApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class ReviewsListGetterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReviewsListGetter_StatusOk() throws Exception {
      mockMvc.perform(get("/movies/20/reviews")
        .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content()
              .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    public void testReviewsListGetter_StatusNotFound() throws Exception {
        mockMvc.perform(get("/movies/21/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLikesListGetter_StatusOk() throws Exception {
        mockMvc.perform(get("/movies/20/likes/user1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]").value(containsInAnyOrder(1, 2)));
    }
    @Test
    public void testLikesListGetter_StatusNotFound() throws Exception {
        mockMvc.perform(get("/movies/21/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
