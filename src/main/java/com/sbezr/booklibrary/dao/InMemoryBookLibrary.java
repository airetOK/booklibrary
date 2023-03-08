package com.sbezr.booklibrary.dao;

import com.sbezr.booklibrary.entity.Book;

import java.util.ArrayList;
import java.util.List;

/** Represents a database.
 * @author airetOK
 * This is a temporary singleton class that acts as a database.
 * Must be deleted after a real database instance will be created.
 */
public class InMemoryBookLibrary {
    private static volatile InMemoryBookLibrary instance;

    private final List<Book> books = new ArrayList<>();

    private InMemoryBookLibrary() {
        for (int i = 1; i <= 100_000; i++) {
            for (int j = 1; j <= 10; j++) {
                books.add(new Book(String.format("Author%d", i), String.format("Title%d", j)));
            }
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public static InMemoryBookLibrary getInstance() {
        InMemoryBookLibrary result = instance;
        if (result != null) {
            return result;
        }
        synchronized(InMemoryBookLibrary.class) {
            if (instance == null) {
                instance = new InMemoryBookLibrary();
            }
            return instance;
        }
    }
}