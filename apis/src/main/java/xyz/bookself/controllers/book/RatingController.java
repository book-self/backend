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
@RequestMapping(RatingController.REQUEST_MAPPING_PATH)
@Slf4j
public class RatingController {
    public static final String REQUEST_MAPPING_PATH = "/v1/books/{bookId}/rating";

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
        if (userDetails == null || !userRepository.existsById(userDetails.getId())) {
            throw new UnauthorizedException();
        }
        var book = bookRepository.findById(bookId).orElseThrow(BadRequestException::new);

        var rating = new Rating(book, userDetails.getId(), ratingDTO.getRating(), ratingDTO.getComment());
        ratingRepository.save(rating);
        return ResponseEntity.accepted().build();
    }


}
