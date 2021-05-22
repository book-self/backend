package xyz.bookself.controllers.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.repository.BookRepository;
import xyz.bookself.books.repository.RatingRepository;
import xyz.bookself.security.WithBookselfUserDetails;
import xyz.bookself.users.domain.User;
import xyz.bookself.users.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String ENDPOINT = RatingController.REQUEST_MAPPING_PATH.replace("{bookId}", "1234");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingRepository ratingRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;

    @Test
    void testSaveNewRating_Unauthorized() throws Exception {
        var ratingDTO = new RatingDTO(5, null);
        var ratingDTOJson = MAPPER.writeValueAsString(ratingDTO);
        mockMvc.perform(post(ENDPOINT).content(ratingDTOJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testSaveNewRating_BadRequestRatingTooLow() throws Exception {
        var ratingDTO = new RatingDTO(-1, null);
        var ratingDTOJson = MAPPER.writeValueAsString(ratingDTO);
        mockMvc.perform(post(ENDPOINT).content(ratingDTOJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveNewRating_BadRequestRatingTooHigh() throws Exception {
        var ratingDTO = new RatingDTO(6, null);
        var ratingDTOJson = MAPPER.writeValueAsString(ratingDTO);
        mockMvc.perform(post(ENDPOINT).content(ratingDTOJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithBookselfUserDetails(id = 1)
    void testSaveNewRating_BadRequestBookNotFound() throws Exception {
        when(bookRepository.findById("1234")).thenReturn(Optional.empty());
        var ratingDTO = new RatingDTO(6, null);
        var ratingDTOJson = MAPPER.writeValueAsString(ratingDTO);
        mockMvc.perform(post(ENDPOINT).content(ratingDTOJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithBookselfUserDetails(id = 1)
    void testSaveNewRating_Success() throws Exception {
        var book = new Book();
        book.setId("1234");
        when(bookRepository.findById("1234")).thenReturn(Optional.of(book));
        var user = new User();
        user.setId(1);
        when(userRepository.existsById(1)).thenReturn(true);
        var ratingDTO = new RatingDTO(3, null);
        var ratingDTOJson = MAPPER.writeValueAsString(ratingDTO);
        mockMvc.perform(post(ENDPOINT).content(ratingDTOJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
        verify(ratingRepository).save(any());
    }

}
