package xyz.bookself.controllers.user;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.bookself.books.repository.BookRepository;
import xyz.bookself.books.repository.RatingRepository;
import xyz.bookself.controllers.book.RatingController;
import xyz.bookself.users.domain.BookList;
import xyz.bookself.users.repository.BookListRepository;
import xyz.bookself.users.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationEngineTest {

    private static final int authenticatedUserId = 1;
    private static final int unauthorizedUser = 0;
    private final String apiPrefix = "/v1/recommendations";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private RatingRepository ratingRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookListRepository bookListRepository;

    @Test
    void whenUnauthorizedUserGoesForRecommendationByAuthorOrGenre_thenStatusIsUnauthorized()
            throws Exception
    {
        final String validBookListId = "99";
        final BookList bookListThatExistsInDatabase = new BookList();
        bookListThatExistsInDatabase.setId(validBookListId);
        mockMvc.perform(get(apiPrefix + "/" + unauthorizedUser).param("recommend-by", "author"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get(apiPrefix + "/" + unauthorizedUser).param("recommend-by", "genre"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void whenAuthorizedUserGoesForRecommendationByAuthor_thenReturnABookBySaidAuthor()
            throws Exception
    {
//        final String validBookListId = "99";
//        final Set<String> setOfBooks = new HashSet<>(Arrays.asList("book-id-1"));
//        final BookList bookListThatExistsInDatabase = new BookList();
//        bookListThatExistsInDatabase.setId(validBookListId);
//
//        when(userRepository.existsById(authenticatedUserId)).thenReturn(true);
//
//        when(bookListRepository.findAllBooksInUserReadBookList(authenticatedUserId)).thenReturn(setOfBooks);



    }

}
