package service;

import com.booklibrary.dao.BookDao;
import com.booklibrary.entity.Book;
import com.booklibrary.entity.BookBuilder;
import com.booklibrary.service.BookService;
import com.booklibrary.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BookServiceTests
{

    private static BookDao bookDao;
    private static BookService bookService;
    private static final BookBuilder builder = new BookBuilder();
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static Book book;

    @BeforeAll
    static void init() {
        bookDao = mock(BookDao.class);
        bookService = new BookServiceImpl(bookDao);
        book = builder.setAuthor(AUTHOR).setTitle(TITLE).build();
    }

    @Test
    public void saveTest() {
        bookService.save(book);
        verify(bookDao).save(book);
    }

    @Test
    public void updateTest() {
        bookService.update(book, AUTHOR, TITLE);
        verify(bookDao).update(book, AUTHOR, TITLE);
    }

    @Test
    public void deleteTest() {
        bookService.delete(AUTHOR, TITLE);
        verify(bookDao).delete(AUTHOR, TITLE);
    }
    @Test
    public void getAllTest() {
        bookService.getAll();
        verify(bookDao).getAll();
    }

}
