package service.delete;

import application.MovieManagerServiceApplication;
import model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import repository.ReviewRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class ReviewDeleteTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    public void reviewDeleteTest_StatusNoContent() throws Exception{

        long countBefore = reviewRepository.count();

        mockMvc.perform(delete("/reviews/1/user1")
        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNoContent());

        Optional<Review> retrievedReview = reviewRepository.findById(1L);
        assertTrue(!retrievedReview.isPresent());

        long countAfter = reviewRepository.count();

        assertTrue(countAfter == countBefore - 1);
    }

    @Test
    @Transactional
    public void reviewDeleteTest_StatusNotFound() throws Exception{

        mockMvc.perform(delete("/reviews/0/user1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    public void reviewDeleteTest_StatusForbidden() throws Exception{

        mockMvc.perform(delete("/reviews/1/user2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }
}
