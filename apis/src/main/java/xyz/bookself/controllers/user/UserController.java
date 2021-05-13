package xyz.bookself.controllers.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xyz.bookself.security.BookselfUserDetails;
import xyz.bookself.users.domain.User;
import xyz.bookself.users.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

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
        // Get the currently logged in user
        final var userDetails = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof BookselfUserDetails)
                .map(BookselfUserDetails.class::cast);

        // If nobody is logged in, UNAUTHORIZED
        if (userDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // If somebody is logged in but is trying to access somebody else's profile, FORBIDDEN
        if (!userDetails.get().getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Otherwise look up the user
        return userRepository.findById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/new-user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User>createNewUser(@RequestBody User newUser){
        newUser.setCreated(LocalDate.now());
        return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.OK);
    }
}