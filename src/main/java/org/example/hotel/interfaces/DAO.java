package org.example.hotel.interfaces;

import java.sql.ResultSet;
import java.util.List;

public interface DAO<T> {
    T getObject(ResultSet resultSet);

    List<Object> getParams(T t);

    List<T> getAll();

    T getOne(int id);

    int getId(T t);

    List<Object> getIds();

    void insert(T t);

    void update(int id, T t);

    void delete(int id);
}
