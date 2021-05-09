package xyz.bookself.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.bookself.books.domain.Book;
import xyz.bookself.config.BookselfApiConfiguration;
import xyz.bookself.users.domain.BookList;
import xyz.bookself.users.domain.BookListEnum;
import xyz.bookself.users.domain.User;
import xyz.bookself.users.repository.BookListRepository;
import xyz.bookself.users.repository.UserRepository;

import java.time.LocalDate;
import java.util.UUID;

@RestController
public class UserController {

    private final BookselfApiConfiguration apiConfiguration;
    private final UserRepository userRepository;


    @Autowired
    public UserController(BookselfApiConfiguration configuration,UserRepository repository) {
        this.userRepository = repository;
        this.apiConfiguration = configuration;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        final User user = userRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/newUser")
    public String createNewUser(){
        User newUser = new User();
        newUser.setUsername("dummyUserName");
        newUser.setEmail(("dummy@dummy.com"));
        newUser.setPasswordHash(UUID.randomUUID().toString().replace("-", ""));
        
        LocalDate date = LocalDate.now();
        newUser.setCreated(date);
        userRepository.save(newUser);

        return "redirect:newBookLists";
    }

}
