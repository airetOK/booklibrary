package dao;

import com.sbezr.booklibrary.dao.AbstractDao;
import com.sbezr.booklibrary.dao.BookDao;
import com.sbezr.booklibrary.dao.InMemoryBookLibrary;
import com.sbezr.booklibrary.entity.Book;
import com.sbezr.booklibrary.exception.BookNotFoundException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoTests {

    private final InMemoryBookLibrary bookLibrary = mock(InMemoryBookLibrary.class);
    private final AbstractDao<Book> dao = new BookDao(bookLibrary);
    static List<Book> books = new ArrayList<>();

    private BookDaoTests() {
        when(bookLibrary.getBooks()).thenReturn(books);
    }

    @BeforeAll
    public static void initBooks() {
        books.add(new Book("Author1", "Book1"));
        books.add(new Book("Author2", "Book2"));
        books.add(new Book("Author3", "Book3"));
        books.add(new Book("Author4", "Book4"));
        books.add(new Book("Author5", "Book5"));
    }

    @Test
    @Order(1)
    public void getAllTest() {
        assertEquals(5, dao.getAll().size());
    }

    @Test
    @Order(2)
    public void saveTest() {
        dao.save(new Book("TestAuthor", "TestTitle"));
        assertEquals(6, dao.getAll().size());
    }

    @Test
    @Order(3)
    public void updateTest() {
        dao.update(new Book("UpdatedAuthor", "UpdatedTitle"), "Author2", "Book2");
        assertEquals(6, dao.getAll().size());
        assertEquals("UpdatedAuthor", dao.getAll().get(1).getAuthor());
    }

    @Test
    @Order(4)
    public void updateThrowExceptionTest() {
        assertThrows(BookNotFoundException.class,
                () -> dao.update(new Book("UpdatedAuthor", "UpdatedTitle"), "NonExist", "Book2"));
    }

    @Test
    @Order(5)
    public void deleteTest() {
        dao.delete("Author3", "Book3");
        dao.delete("Author4", "Book4");
        assertEquals(4, dao.getAll().size());
    }

    @Test
    @Order(6)
    public void deleteThrowExceptionTest() {
        assertThrows(BookNotFoundException.class,
                () -> dao.delete("NonExist", "Book2"));
        assertEquals(4, dao.getAll().size());
    }

    @AfterAll
    public static void clear() {
        books = null;
    }
}
