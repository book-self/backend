package xyz.bookself.controllers.recommendationEngine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.bookself.books.domain.Author;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.repository.AuthorRepository;
import xyz.bookself.books.repository.BookRepository;
import xyz.bookself.config.BookselfApiConfiguration;
import xyz.bookself.controllers.book.BookDTO;
import xyz.bookself.users.domain.BookList;
import xyz.bookself.users.repository.BookListRepository;

import java.util.*;
import java.util.stream.Collectors;

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
    public ResponseEntity<Collection<BookDTO>> getRecommendation(@PathVariable("id") Integer userId, @RequestParam(name = "recommend-by", required = true) Integer recommendBy) {

        Collection<String> readBookListId = bookListRepository.findAllBooksInUserReadBookList(userId);

        if(readBookListId.size() != 0)
        {
            if(recommendBy != null)
            {
                final Collection<String> informationCollection = new HashSet<>();
                if(recommendBy == 0)
                {
                    //recommend by author
                    Set<Author> foundAuthors;
                    for(String bookId : readBookListId) {
                        foundAuthors = bookRepository.findById(bookId).orElseThrow().getAuthors();
                        for(Author author: foundAuthors)
                        {
                            informationCollection.add(author.getId());
                        }

                    }

                    String authorId = informationCollection.stream().findFirst().get();

                    final var books = bookRepository.findAllByAuthor(authorId, apiConfiguration.getMaxReturnedBooks())
                            .stream().map(BookDTO::new).collect(Collectors.toSet());
                    return new ResponseEntity<>(books, HttpStatus.OK);
                }
                else if (recommendBy == 1)
                {
                    //need all the genres that user had read
                    Set<String> genreList;
                    for(String bookId : readBookListId) {
                        genreList = bookRepository.findById(bookId).orElseThrow().getGenres();
                        for (String genre : genreList)
                        {
                            informationCollection.add(genre);
                        }
                    }

                    String genre = informationCollection.stream().findFirst().get();
                    final var books = bookRepository.findAllByGenre(genre, apiConfiguration.getMaxReturnedBooks())
                            .stream().map(BookDTO::new).collect(Collectors.toSet());
                    return new ResponseEntity<>(books, HttpStatus.OK);
                }
            }


        }


        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
