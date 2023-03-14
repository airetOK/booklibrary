package dao;

import com.sbezr.booklibrary.dao.BookDao;
import com.sbezr.booklibrary.entity.BookBuilder;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookDaoTests
{

    private final BookDao bookDao = mock(BookDao.class);
    private final BookBuilder builder = new BookBuilder();
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";

    @Test
    public void saveTest() {
        doNothing().when(bookDao).save(builder
            .setAuthor(AUTHOR)
            .setTitle(TITLE)
            .build());
    }

    @Test
    public void updateTest() {
        doNothing().when(bookDao).update(builder
            .setAuthor("updatedAuthor")
            .setTitle("updatedTitle")
            .build(), AUTHOR, TITLE);
    }

    @Test
    public void deleteTest() {
        doNothing().when(bookDao).delete(AUTHOR, TITLE);
    }

    @Test
    public void getAllTest() {
        when(bookDao.getAll()).thenReturn(Collections.emptyList());
    }

}
