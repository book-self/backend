package xyz.bookself.controllers.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.bookself.books.domain.Author;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.repository.BookRepository;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v1/book")
@Slf4j
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository repository) {
        this.bookRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        final Book book = bookRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Collection<Book>> getBooksByAuthor(@RequestParam(name = "authorId") Optional<String> id) {
        final String authorId = id.orElse("");
        final Author a = new Author();
        a.setId(authorId);
        final Collection<Book> books = bookRepository.findAllByAuthorsContains(a);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
    }
}
