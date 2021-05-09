package xyz.bookself.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.bookself.books.domain.Author;
import xyz.bookself.books.domain.Book;

import java.util.Collection;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, String> {
    Collection<Book> findAllByAuthorsContains(Author author);
    Collection<Book> findAllByGenresIn(Set<String> genres);
    @Query(nativeQuery = true, value = "SELECT * FROM books LIMIT 10")
    Collection<Book> findTenBooks();
}
