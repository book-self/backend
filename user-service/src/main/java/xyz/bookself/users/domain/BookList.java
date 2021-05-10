package xyz.bookself.users.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book_lists")
public class BookList {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private BookListEnum listType;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "books_in_list", joinColumns = @JoinColumn(name = "list_id"))
    @Column(name = "book_in_list")
    private Set<String> books= new HashSet<>();

    public void addBook(String bookId)
    {
        if(!(books.contains(bookId))) {
            books.add(bookId);
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        }
        else {
//            previously used setString, but this causes postgresql to bark about incompatible types.
//           now using setObject passing in the java type for the postgres enum object
//            st.setString(index,((Enum) value).name());
            st.setObject(index,((Enum) value), Types.OTHER);
        }

    }
}



