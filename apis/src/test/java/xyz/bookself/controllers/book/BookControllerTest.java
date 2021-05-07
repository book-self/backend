package xyz.bookself.controllers.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.bookself.books.domain.Author;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.repository.BookRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void givenBookExists_whenIdIsSuppliedToBookEndpoint_thenBookIsReturned()
            throws Exception {
        final String validBookId = "9999999999";
        final Book bookThatExistsInDatabase = new Book();
        bookThatExistsInDatabase.setId(validBookId);
        when(bookRepository.findById(validBookId)).thenReturn(Optional.of(bookThatExistsInDatabase));
        mockMvc.perform(get("/v1/book/" + validBookId))
                .andExpect(status().isOk());
    }

    @Test
    void givenBookDoesNotExist_whenBookIsPosted_thenSaveEndpointReturnsBook()
            throws Exception {

        final Book newBook = new Book();
        final String id = "999999999";
        final int pages = 100;
        newBook.setId(id);
        newBook.setPages(pages);

        when(bookRepository.save(newBook)).thenReturn(newBook);

        final String jsonContent = TestUtilities.toJsonString(newBook);

        mockMvc.perform(
                post("/v1/book/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    @Test
    void givenAuthorId_whenGetRequestedOnBookList_thenAllBooksWrittenByAuthorAreReturned()
            throws Exception {

        final String validAuthorId = "12345";
        final Author author = new Author();
        author.setId(validAuthorId);
        final Set<Book> books = IntStream.range(100, 110)
                .mapToObj(i -> {
                    final Book b = new Book();
                    b.setId(Integer.toHexString(i));
                    b.setAuthors(new HashSet<>(Collections.singletonList(author)));
                    return b;
                }).collect(Collectors.toSet());
        final String jsonContent = TestUtilities.toJsonString(books);

        when(bookRepository.findAllByAuthorsContains(author)).thenReturn(books);

        mockMvc.perform(get("/v1/book/list?authorId=" + validAuthorId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }
}
