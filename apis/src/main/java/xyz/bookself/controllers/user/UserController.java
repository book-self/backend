package xyz.bookself.controllers.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import xyz.bookself.users.domain.User;
import xyz.bookself.users.repository.UserRepository;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserRepository repository) {
        this.userRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        final User user = userRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public ResponseEntity<User>  createNewUser(@RequestParam String newUserEmail, @RequestParam String newUserName){
        User newUser = new User();
        newUser.setUsername(newUserName);
        newUser.setEmail(newUserEmail);
        newUser.setPasswordHash(UUID.randomUUID().toString().replace("-", ""));

        LocalDate date = LocalDate.now();
        newUser.setCreated(date);
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

}