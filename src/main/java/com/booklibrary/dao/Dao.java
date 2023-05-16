package com.booklibrary.dao;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    void save(T t);

    void update(T t, String author, String title);

    void delete(String author, String title);

}
