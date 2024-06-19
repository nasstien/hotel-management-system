package org.example.hotel.dao;

import org.example.hotel.interfaces.DAO;
import org.example.hotel.utils.Util;

import java.sql.ResultSet;

import java.util.List;
import java.util.Collections;

import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.database;

public abstract class BaseDAO<T> implements DAO<T> {
    protected abstract String getTableName();
    protected abstract List<String> getColumnNames();

    public abstract T getObject(ResultSet resultSet);
    public abstract List<Object> getParams(T t);

    @Override
    public List<T> getAll() {
        String sql = STR."SELECT * FROM \{getTableName()} " +
                         "WHERE hotel_id = ? " +
                         "ORDER BY created_at DESC";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getObjects(sql, params, this::getObject);
    }

    @Override
    public T getOne(int id) {
        String sql = STR."SELECT * FROM \{getTableName()} " +
                         "WHERE id = ? AND hotel_id = ?";
        List<Object> params = List.of(id, new HotelDAO().getId(hotel));
        return database.getObject(sql, params, this::getObject);
    }

    @Override
    public int getId(T t) {
        String separator = " = ? AND ";
        int trimLength = separator.length() - separator.indexOf('A');
        String keys = Util.joinStrings(separator, getColumnNames(), trimLength);
        String sql = STR."SELECT id FROM \{getTableName()} " +
                     STR."WHERE \{keys}";
        List<Object> params = getParams(t);
        return (int) database.getSingleValue(sql, params, "id");
    }

    @Override
    public List<Object> getIds() {
        String sql = STR."SELECT id FROM \{getTableName()} " +
                         "WHERE hotel_id = ? " +
                         "ORDER BY id";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getList(sql, params);
    }

    @Override
    public void insert(T t) {
        String separator = ", ";
        String keys = Util.joinStrings(separator, getColumnNames(), separator.length());
        String placeholders = Util.joinStrings(separator, Collections.nCopies(getColumnNames().size(), "?"), separator.length());
        String sql = STR."INSERT INTO \{getTableName()} (\{keys}) " +
                     STR."VALUES (\{placeholders})";
        List<Object> params = getParams(t);
        database.update(sql, params);
    }

    @Override
    public void update(int id, T t) {
        String separator = " = ?, ";
        int trimLength = separator.length() - separator.indexOf(',');
        String keys = Util.joinStrings(separator, getColumnNames(), trimLength);
        String sql = STR."UPDATE \{getTableName()} " +
                     STR."SET \{keys} " +
                     STR."WHERE id = \{id}";
        List<Object> params = getParams(t);
        database.update(sql, params);
    }

    @Override
    public void delete(int id) {
        String sql = STR."DELETE FROM \{getTableName()} " +
                         "WHERE id = ?";
        List<Object> params = List.of(id);
        database.update(sql, params);
    }
}
