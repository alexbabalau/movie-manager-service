package service.post;

import application.MovieManagerServiceApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Review;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import repository.ReviewRepository;
import stub.review.ReviewStub;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class ReviewsPostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void reviewPostTest_StatusOk() throws Exception {
        Review review = ReviewStub.getReview();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedReview = objectMapper.writeValueAsString(review);
        ResultActions postResult = mockMvc.perform(post("/reviews/218/user3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializedReview))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(review.getUsername())))
                .andExpect(jsonPath("$.stars", is(review.getStars())))
                .andExpect(jsonPath("$.date", is(dateFormat.format(review.getDate()))))
                .andExpect(jsonPath("$.comment", is(review.getComment())));
        MvcResult result = postResult.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Review responseReview = objectMapper.readValue(contentAsString, Review.class);

        Review databaseReview = reviewRepository.findById(responseReview.getId()).orElse(null);

        assertNotNull(databaseReview);

        assertEquals(databaseReview.getUsername(), review.getUsername());
        assertEquals(dateFormat.format(databaseReview.getDate()), dateFormat.format(review.getDate()));
        assertEquals(databaseReview.getComment(), review.getComment());
        assertEquals(databaseReview.getStars(), review.getStars());
    }

    @Test
    public void reviewPostTest_StatusNotFound() throws Exception {
        Review review = ReviewStub.getReview();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedReview = objectMapper.writeValueAsString(review);
        ResultActions postResult = mockMvc.perform(post("/reviews/217/user3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializedReview))
                .andExpect(status().isNotFound());
    }

    @Test
    public void reviewPostTest_StatusForbidden() throws Exception {
        Review review = ReviewStub.getReview();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedReview = objectMapper.writeValueAsString(review);
        ResultActions postResult = mockMvc.perform(post("/reviews/218/user1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializedReview))
                .andExpect(status().isForbidden());
    }
}
