package xyz.bookself.controllers.booklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.bookself.books.domain.Book;
import xyz.bookself.config.BookselfApiConfiguration;
import xyz.bookself.users.domain.BookList;
import xyz.bookself.users.repository.BookListRepository;
import xyz.bookself.users.repository.UserRepository;

@RestController
public class BookListController
{
    private final BookListRepository bookListRepository;
    private final BookselfApiConfiguration apiConfiguration;

    @Autowired
    public BookListController(BookselfApiConfiguration configuration, BookListRepository repository) {
        this.bookListRepository = repository;
        this.apiConfiguration = configuration;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookList> getBook(@PathVariable String bookListId) {
        final BookList bookList = bookListRepository.findById(bookListId).orElseThrow();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<BookList>addBookToList(@RequestParam String bookListId)
    {

    }
}
