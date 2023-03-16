package dao;

import com.sbezr.booklibrary.dao.BookDao;
import com.sbezr.booklibrary.dao.Dao;
import com.sbezr.booklibrary.dao.InMemoryBookLibrary;
import com.sbezr.booklibrary.entity.Book;
import com.sbezr.booklibrary.entity.BookBuilder;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookDaoTests
{

    private final InMemoryBookLibrary bookLibrary = mock(InMemoryBookLibrary.class);
    private final Dao<Book> bookDao = new BookDao(bookLibrary);
    private final BookBuilder builder = new BookBuilder();
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";

    @Test
    public void saveTest() {
        doNothing().when(bookLibrary).save(builder
            .setAuthor(AUTHOR)
            .setTitle(TITLE)
            .build());
        bookDao.save(builder
            .setAuthor(AUTHOR)
            .setTitle(TITLE)
            .build());
    }

    @Test
    public void updateTest() {
        doNothing().when(bookLibrary).update(builder
            .setAuthor("updatedAuthor")
            .setTitle("updatedTitle")
            .build(), AUTHOR, TITLE);
        bookDao.update(builder
            .setAuthor("updatedAuthor")
            .setTitle("updatedTitle")
            .build(), AUTHOR, TITLE);
    }

    @Test
    public void deleteTest() {
        doNothing().when(bookLibrary).delete(AUTHOR, TITLE);
        bookDao.delete(AUTHOR, TITLE);
    }

    @Test
    public void getAllTest() {
        when(bookLibrary.getAll()).thenReturn(Collections.emptyList());
        bookDao.getAll();
    }

}
