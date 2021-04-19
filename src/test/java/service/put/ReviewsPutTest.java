package service.put;

import application.MovieManagerServiceApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import repository.ReviewRepository;
import stub.review.ReviewStub;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class ReviewsPutTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private String getSerializedReview(Review review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedReview = objectMapper.writeValueAsString(review);
        return serializedReview;
    }

    @Test
    @Transactional
    public void updateReviewTest_StatusNoContent() throws Exception{
        Review newReview = new Review(1L, 5, new Date(), "Very nice!", "user1", null, null, null);


        mockMvc.perform(put("/reviews/1/user1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(getSerializedReview(newReview)))
            .andExpect(status().isNoContent());
        Review retrievedReview = reviewRepository.findById(1L).orElse(null);

        assertNotNull(retrievedReview);
        assertEquals(retrievedReview.getStars(), newReview.getStars());
        assertEquals(retrievedReview.getComment(), newReview.getComment());
        assertEquals(retrievedReview.getUsername(), newReview.getUsername());
        assertEquals(dateFormat.format(retrievedReview.getDate()), dateFormat.format(newReview.getDate()));
    }

    @Test
    @Transactional
    public void updateReviewTest_StatusNotFound() throws Exception{
        Review newReview = new Review(0L, 5, new Date(), "Veri nice!", "user1", null, null, null);
        mockMvc.perform(put("/reviews/0/user1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSerializedReview(newReview)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateReviewTest_StatusForbidden() throws Exception{
        Review newReview = new Review(0L, 5, new Date(), "Veri nice!", "user1", null, null, null);
        mockMvc.perform(put("/reviews/1/user2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSerializedReview(newReview)))
                .andExpect(status().isForbidden());
    }
}
