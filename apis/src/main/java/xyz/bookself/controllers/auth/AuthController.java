package xyz.bookself.controllers.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import xyz.bookself.security.BookselfUserDetails;

import xyz.bookself.users.domain.User;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {

    @GetMapping("/signin")
    public ResponseEntity<User> signIn(@AuthenticationPrincipal BookselfUserDetails userDetails) {
        // If nobody is logged in, UNAUTHORIZED
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

