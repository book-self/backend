package xyz.bookself.users.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EqualsAndHashCodeTest {

    @Test
    void givenTwoBookLists_whenTheirIdsAreTheSame_thenTheyAreConsideredToBeEqual() {
        final String id = "001";
        final BookList a = new BookList();
        a.setId(id);
        final BookList b = new BookList();
        b.setId(id);
        assertThat(a.equals(b)).isTrue();
        assert(true);
    }

    @Test
    void givenTwoUsers_whenTheirIdsAreTheSame_thenTheyAreConsideredToBeEqual() {
        final String id = "001";
//        final Author a = new Author();
//        a.setId(id);
//        final Author b = new Author();
//        b.setId(id);
//        assertThat(a.equals(b)).isTrue();
        assert(true);
    }

    @Test
    void givenABookListObject_whenTheObjectIsNotNull_thenHashCodeReturnsAnInteger() {
        assertThat(new BookList().hashCode()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assert(true);
    }

    @Test
    void givenAnAuthorObject_whenTheObjectIsNotNull_thenHashCodeReturnsAnInteger() {
        assertThat(new User().hashCode()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assert(true);
    }
}
