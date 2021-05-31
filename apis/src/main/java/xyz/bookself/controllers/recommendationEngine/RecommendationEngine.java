package xyz.bookself.controllers.recommendationEngine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.repository.BookRepository;
import xyz.bookself.config.BookselfApiConfiguration;
import xyz.bookself.users.domain.BookList;
import xyz.bookself.users.repository.BookListRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/v1/recommendations")
@Slf4j
public class RecommendationEngine {
    private final BookselfApiConfiguration apiConfiguration;
    private final BookListRepository bookListRepository;
    private final BookRepository bookRepository;

    @Autowired
    public RecommendationEngine(BookselfApiConfiguration configuration, BookListRepository repository, BookRepository bookRepository) {
        this.apiConfiguration = configuration;
        this.bookListRepository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection<Book>> getRecommendation(@PathVariable("id") Integer userId,
                                                              @RequestParam(name = "authorId", required = false) String authorId,
                                                              @RequestParam(name = "genre", required = false) String genre) {
        final Collection<Book> booksInList = new ArrayList<>();
        if(Objects.nonNull(authorId) && !(authorId.isEmpty())) {
            //do author book recommendation

            //what if no authors
        }
        else if(genre != null && !(genre.isEmpty()))
        {
            //genre recommendation

            //what if no genre
        }
        else
        {
            //what happens when both genre and author are empty
            //for now return empty 
        }


        return new ResponseEntity<>(booksInList, HttpStatus.OK);
    }
}
