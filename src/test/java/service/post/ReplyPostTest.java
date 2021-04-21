package service.post;

import application.MovieManagerServiceApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Reply;
import model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import repository.ReplyRepository;
import stub.review.ReviewStub;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = MovieManagerServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class ReplyPostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    @Transactional
    public void addReply_StatusOk() throws Exception {
        Reply reply = new Reply(null, "user2", "Nice review", new Date(), null);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedReview = objectMapper.writeValueAsString(reply);
        ResultActions postResult = mockMvc.perform(post("/replies/1/user2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializedReview))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(reply.getUsername())))
                .andExpect(jsonPath("$.content", is(reply.getContent())))
                .andExpect(jsonPath("$.date", is(dateFormat.format(reply.getDate()))));
        MvcResult result = postResult.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Reply responseReply = objectMapper.readValue(contentAsString, Reply.class);

        Reply databaseReply = replyRepository.findById(responseReply.getId()).orElse(null);

        assertNotNull(databaseReply);

        assertEquals(databaseReply.getUsername(), reply.getUsername());
        assertEquals(dateFormat.format(databaseReply.getDate()), dateFormat.format(reply.getDate()));
        assertEquals(databaseReply.getContent(), reply.getContent());
    }

    @Test
    @Transactional
    public void addReply_StatusNotFound() throws Exception {
        Reply reply = new Reply(null, "user2", "Nice review", new Date(), null);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedReview = objectMapper.writeValueAsString(reply);
        ResultActions postResult = mockMvc.perform(post("/replies/0/user2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializedReview))
                .andExpect(status().isNotFound());
    }
}
