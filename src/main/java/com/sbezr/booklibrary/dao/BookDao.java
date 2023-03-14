package com.sbezr.booklibrary.dao;

import com.sbezr.booklibrary.entity.Book;
import java.util.List;

public class BookDao implements Dao<Book>
{

    private final InMemoryBookLibrary bookLibrary;

    public BookDao(InMemoryBookLibrary bookLibrary) {
        this.bookLibrary = bookLibrary;
    }

    @Override
    public List<Book> getAll() {
        return bookLibrary.getAll();
    }

    @Override
    public void save(Book book) {
        bookLibrary.save(book);
    }

    @Override
    public void update(Book book, String author, String title) {
        bookLibrary.update(book, author, title);
    }

    @Override
    public void delete(String author, String title) {
        bookLibrary.delete(author, title);
    }

}
