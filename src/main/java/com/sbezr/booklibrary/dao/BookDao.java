package com.sbezr.booklibrary.dao;

import com.sbezr.booklibrary.entity.Book;
import com.sbezr.booklibrary.exception.BookNotFoundException;

import java.util.List;
import java.util.stream.IntStream;

public class BookDao implements AbstractDao<Book> {

    private final InMemoryBookLibrary dataSource;

    public BookDao(InMemoryBookLibrary dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> getAll() {
        return dataSource.getBooks();
    }

    @Override
    public void save(Book book) {
        dataSource.getBooks().add(book);
    }

    @Override
    public void update(Book book, String author, String title) {
        int index = getIndexByAuthorAndTitle(dataSource.getBooks(), author, title);
        dataSource.getBooks().set(index, book);
    }

    @Override
    public void delete(String author, String title) {
        int index = getIndexByAuthorAndTitle(dataSource.getBooks(), author, title);
        dataSource.getBooks().remove(index);
    }

    private int getIndexByAuthorAndTitle(List<Book> books, String author, String title) {
        return IntStream.range(0, books.size())
                .filter(index -> books.get(index).getAuthor().equals(author) &&
                        books.get(index).getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(
                        String.format("The book wasn't found by author %s and title %s", author, title)
                ));
    }
}
