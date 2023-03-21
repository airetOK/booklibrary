package com.sbezr.booklibrary.service.impl;

import com.sbezr.booklibrary.dao.BookDao;
import com.sbezr.booklibrary.entity.Book;
import com.sbezr.booklibrary.service.BookService;
import java.util.List;

public class BookServiceImpl implements BookService
{

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao)
    {
        this.bookDao = bookDao;
    }

    @Override
    public void save(Book book)
    {
        bookDao.save(book);
    }

    @Override
    public void update(Book book, String author, String title)
    {
        bookDao.update(book, author, title);
    }

    @Override
    public void delete(String author, String title)
    {
        bookDao.delete(author, title);
    }

    @Override
    public List<Book> getAll()
    {
        return bookDao.getAll();
    }

}
