package org.example.hotel.utils;

import org.example.hotel.interfaces.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.hotel.Application.database;

public class Database {
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private Connection connection;

    public Database(String url, String user, String password) {
        this.URL = url;
        this.USER = user;
        this.PASSWORD = password;
        this.connection = null;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void close() {
        try {
            if(connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void closeResources(PreparedStatement statement) {
        closeResources(statement, null);
    }

    public void closeResources(PreparedStatement statement, ResultSet resultSet) {
        try {
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public <T> List<T> getObjects(String sql, List<Object> params, RowMapper<T> mapper) {
        List<T> rows = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = database.getConnection().prepareStatement(sql);
            if (params != null) {
                Util.setParams(statement, params);
            }

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rows.add(mapper.mapRow(resultSet));
            }

            return rows;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            database.closeResources(statement, resultSet);
        }

        return null;
    }

    public <T> T getObject(String sql, List<Object> params, RowMapper<T> mapper) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = database.getConnection().prepareStatement(sql);
            if (params != null) {
                Util.setParams(statement, params);
            }

            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            database.closeResources(statement, resultSet);
        }

        return null;
    }

    public Object getSingleValue(String sql, List<Object> params, String columnName) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = database.getConnection().prepareStatement(sql);
            if (params != null) {
                Util.setParams(statement, params);
            }

            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getObject(columnName);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            database.closeResources(statement, resultSet);
        }

        return null;
    }

    public List<Object> getList(String sql, List<Object> params) {
        List<Object> rows = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = database.getConnection().prepareStatement(sql);

            if (params != null) {
                Util.setParams(statement, params);
            }

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rows.add(resultSet.getObject(1));
            }

            return rows;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            database.closeResources(statement, resultSet);
        }

        return null;
    }

    public List<Object[]> getColumnRows(String sql, List<Object> params) {
        List<Object[]> rows = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = database.getConnection().prepareStatement(sql);

            if (params != null) {
                Util.setParams(statement, params);
            }

            resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }

                rows.add(row);
            }

            return rows;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            database.closeResources(statement, resultSet);
        }

        return null;
    }

    public void update(String sql, List<Object> params) {
        PreparedStatement statement = null;

        try {
            statement = database.getConnection().prepareStatement(sql);
            if (params != null) {
                Util.setParams(statement, params);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            database.closeResources(statement);
        }
    }
}
