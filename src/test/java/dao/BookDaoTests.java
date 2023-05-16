package dao;

import com.booklibrary.dao.BookDao;
import com.booklibrary.dao.Dao;
import com.booklibrary.dao.InMemoryBookLibrary;
import com.booklibrary.entity.Book;
import com.booklibrary.entity.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class BookDaoTests
{

    private static InMemoryBookLibrary bookLibrary;
    private static Dao<Book> bookDao;
    private static final BookBuilder builder = new BookBuilder();
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static Book book;

    @BeforeAll
    public static void init() {
        bookLibrary = mock(InMemoryBookLibrary.class);
        bookDao = new BookDao(bookLibrary);
        book = builder.setAuthor(AUTHOR).setTitle(TITLE).build();
    }

    @Test
    public void saveTest() {
        bookDao.save(book);
        verify(bookLibrary).save(book);
    }

    @Test
    public void updateTest() {
        bookDao.update(book, AUTHOR, TITLE);
        verify(bookLibrary).update(book, AUTHOR, TITLE);
    }

    @Test
    public void deleteTest() {
        bookDao.delete(AUTHOR, TITLE);
        verify(bookLibrary).delete(AUTHOR, TITLE);
    }

    @Test
    public void getAllTest() {
        bookDao.getAll();
        verify(bookLibrary).getAll();
    }

}
