package com.sbezr.booklibrary.dao;

import com.sbezr.booklibrary.entity.Book;
import com.sbezr.booklibrary.entity.BookBuilder;
import com.sbezr.booklibrary.exception.BookNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/** Represents a database.
 * @author airetOK
 */
public class InMemoryBookLibrary {

    private static final BookBuilder builder = new BookBuilder();

    private final List<Book> books;

    public InMemoryBookLibrary() {
        books = Collections.synchronizedList(new ArrayList<>());

        for (int i = 1; i <= 100_000; i++) {
            for (int j = 1; j <= 10; j++) {
                books.add(builder
                    .setAuthor(String.format("Author%d", i))
                    .setTitle(String.format("Title%d", j))
                    .build());
            }
        }
    }

    /* This constructor is used for testing */
    public InMemoryBookLibrary(List<Book> books) {
        this.books = Collections.synchronizedList(books);
    }

    public void save(Book book) {
        books.add(book);
    }

    public void update(Book book, String author, String title) {
        int index = getIndexByAuthorAndTitle(author, title);
        books.set(index, book);
    }

    public void delete(String author, String title) {
        int index = getIndexByAuthorAndTitle(author, title);
        books.remove(index);
    }

    public List<Book> getAll() {
        return books;
    }

    private int getIndexByAuthorAndTitle(String author, String title) {
        return IntStream.range(0, books.size())
            .filter(index -> books.get(index).getAuthor().equals(author) &&
                books.get(index).getTitle().equals(title))
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException(
                String.format("The book wasn't found by author %s and title %s", author, title)
            ));
    }

}