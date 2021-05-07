package xyz.bookself.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bookself.books.domain.Author;
import xyz.bookself.books.domain.Book;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, String> {
    Collection<Book> findAllByAuthorsContains(Author author);
}
