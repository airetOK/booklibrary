package dao;

import com.booklibrary.dao.InMemoryBookLibrary;
import com.booklibrary.entity.Book;
import com.booklibrary.entity.BookBuilder;
import com.booklibrary.exception.BookNotFoundException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InMemoryBookLibraryTests
{

    private static InMemoryBookLibrary bookLibrary;
    static List<Book> books = new ArrayList<>();
    private static final BookBuilder builder = new BookBuilder();

    @BeforeAll
    public static void init() {
        books.add(builder.setAuthor("Author1").setTitle("Book1").build());
        books.add(builder.setAuthor("Author2").setTitle("Book2").build());
        books.add(builder.setAuthor("Author3").setTitle("Book3").build());
        books.add(builder.setAuthor("Author4").setTitle("Book4").build());
        books.add(builder.setAuthor("Author5").setTitle("Book5").build());
        bookLibrary = new InMemoryBookLibrary(books);
    }

    @Test
    @Order(1)
    public void getAllTest() {
        assertEquals(5, bookLibrary.getAll().size());
    }

    @Test
    @Order(2)
    public void saveTest() {
        bookLibrary.save(builder.setAuthor("TestAuthor").setTitle("TestTitle").build());
        assertEquals(6, bookLibrary.getAll().size());
    }

    @Test
    @Order(3)
    public void updateTest() {
        bookLibrary.update(builder.setAuthor("UpdatedAuthor").setTitle("UpdatedTitle").build(),
            "Author2", "Book2");
        assertEquals(6, bookLibrary.getAll().size());
        assertEquals("UpdatedAuthor", bookLibrary.getAll().get(1).getAuthor());
    }

    @Test
    @Order(4)
    public void updateThrowExceptionTest() {
        assertThrows(BookNotFoundException.class,
                () -> bookLibrary.update(builder
                    .setAuthor("UpdatedAuthor")
                    .setTitle("UpdatedTitle")
                    .build(), "NonExist", "Book2"));
    }

    @Test
    @Order(5)
    public void deleteTest() {
        bookLibrary.delete("Author3", "Book3");
        bookLibrary.delete("Author4", "Book4");
        assertEquals(4, bookLibrary.getAll().size());
    }

    @Test
    @Order(6)
    public void deleteThrowExceptionTest() {
        assertThrows(BookNotFoundException.class,
                () -> bookLibrary.delete("NonExist", "Book2"));
        assertEquals(4, bookLibrary.getAll().size());
    }

    @AfterAll
    public static void clear() {
        books = null;
    }

}
