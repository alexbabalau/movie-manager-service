package service.delete;

import application.MovieManagerServiceApplication;
import model.Like;
import model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import repository.LikeRepository;
import repository.ReviewRepository;
import java.util.List;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class ReviewDeleteTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Test
    @Transactional
    public void reviewDeleteTest_StatusNoContent() throws Exception{

        long countBefore = reviewRepository.count();

        mockMvc.perform(delete("/reviews/1/user1")
                .header("authorization", "mockToken")
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
                .header("authorization", "mockToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    public void reviewDeleteTest_StatusForbidden() throws Exception{

        mockMvc.perform(delete("/reviews/1/user2")
                .header("authorization", "mockToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @Transactional
    public void likeDeleteTest_StatusNoContent() throws Exception{
        mockMvc.perform(delete("/likes/1/user1")
                .header("authorization", "mockToken"))
                .andExpect(status().isNoContent());
        List<Like> likes = likeRepository.findLikesByReviewIdAnsUsername(1L, "user1");
        assertNotNull(likes);
        assertEquals(0, likes.size());
    }

    @Test
    @Transactional
    public void likeDeleteTest_StatusNotFound() throws Exception{
        mockMvc.perform(delete("/likes/1/user0")
                .header("authorization", "mockToken"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/likes/0/user1")
                .header("authorization", "mockToken"))
                .andExpect(status().isNotFound());
    }
}
