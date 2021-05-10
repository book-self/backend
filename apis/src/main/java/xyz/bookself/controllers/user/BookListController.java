package xyz.bookself.controllers.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import xyz.bookself.config.BookselfApiConfiguration;
import xyz.bookself.users.domain.BookList;
import xyz.bookself.users.domain.BookListEnum;
import xyz.bookself.users.repository.BookListRepository;

import java.util.UUID;

@RestController
@RequestMapping("/v1/bookLists")
@Slf4j
public class BookListController
{
    private final BookListRepository bookListRepository;

    @Autowired
    public BookListController(BookListRepository repository) {
        this.bookListRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookList> getBookList(@PathVariable String id) {
        final BookList booklist = bookListRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(booklist, HttpStatus.OK);
    }

    @GetMapping("/newBookLists")
    public ResponseEntity<BookList>generateBookList(){
        BookList newDNF = new BookList();
        newDNF.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 24));
        newDNF.setListType(BookListEnum.DNF);
        bookListRepository.save(newDNF);
        return new ResponseEntity<>(newDNF, HttpStatus.OK);

    }

}