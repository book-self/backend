package xyz.bookself.controllers.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import xyz.bookself.books.domain.Rating;
import xyz.bookself.books.repository.BookRepository;
import xyz.bookself.books.repository.RatingRepository;
import xyz.bookself.exceptions.BadRequestException;
import xyz.bookself.exceptions.UnauthorizedException;
import xyz.bookself.security.BookselfUserDetails;
import xyz.bookself.users.repository.UserRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/books/{bookId}/rating")
@Slf4j
public class RatingController {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public RatingController(RatingRepository ratingRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<Void> saveNewRating(@PathVariable("bookId") String bookId,
                                              @AuthenticationPrincipal BookselfUserDetails userDetails,
                                              @RequestBody @Valid RatingDTO ratingDTO) {
        if (userDetails == null) {
            throw new UnauthorizedException();
        }
        var user = userRepository.findById(userDetails.getId()).orElseThrow(UnauthorizedException::new);
        var book = bookRepository.findById(bookId).orElseThrow(BadRequestException::new);

        var rating = new Rating(book, user, ratingDTO.getRating(), ratingDTO.getComment());
        ratingRepository.save(rating);
        return ResponseEntity.accepted().build();
    }


}
