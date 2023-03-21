package com.sbezr.booklibrary.service;

import com.sbezr.booklibrary.entity.Book;
import java.util.List;

public interface BookService
{

    void save(Book book);
    void update(Book book, String author, String title);
    void delete(String author, String title);
    List<Book> getAll();

}
