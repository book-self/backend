package xyz.bookself.controllers.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import xyz.bookself.config.BookselfApiConfiguration;
import xyz.bookself.users.domain.BookList;
import xyz.bookself.users.domain.BookListEnum;
import xyz.bookself.users.domain.User;
import xyz.bookself.users.repository.BookListRepository;

import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/v1/book-lists")
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

    @PostMapping("/new-book-lists")
    @ResponseBody
    public ResponseEntity<BookList>generateBookList(){
        BookList newDNF = new BookList();
        newDNF.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 24));
        newDNF.setListType(BookListEnum.DNF);
        bookListRepository.save(newDNF);
        return new ResponseEntity<>(newDNF, HttpStatus.OK);
    }

    @PostMapping(value = "/add-book-to-list", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<BookList>addBookToList(@RequestBody BookIdListIdDTO bookIdListIdDTO)
    {
        BookList foundBookList = bookListRepository.findById(bookIdListIdDTO.getListId()).orElseThrow();
        Set<String> booksInList = foundBookList.getBooks();
        booksInList.add(bookIdListIdDTO.getBookId());
        foundBookList.setBooks(booksInList);
        return new ResponseEntity<>(foundBookList, HttpStatus.OK);
    }
}

class BookIdListIdDTO
{
    private String bookId;
    private String listId;

    public String getBookId() {
        return bookId;
    }

    public String getListId() {
        return listId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}