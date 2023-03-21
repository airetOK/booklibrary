package com.sbezr.booklibrary.entity;

import java.util.Objects;

public class BookBuilder
{
    private String author;
    private String title;

    public BookBuilder()
    {
    }

    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Book build() {
        if (Objects.isNull(title) && Objects.isNull(author)) {
            throw new RuntimeException("Book cannot be created. Please, define author and title.");
        }
        return new Book(author, title);
    }

}
