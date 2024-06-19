package org.example.hotel.dao;

import org.example.hotel.enums.Role;
import org.example.hotel.models.User;
import org.example.hotel.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Objects;

import static org.example.hotel.Application.database;

public class UserDAO extends BaseDAO<User> {
    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "hotel_id",
                "first_name",
                "last_name",
                "email",
                "phone_num",
                "passport_num",
                "role",
                "user_position",
                "salary",
                "password",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public User getObject(ResultSet resultSet) {
        try {
            return new User(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_num"),
                    resultSet.getString("passport_num"),
                    Role.valueOf(resultSet.getString("role").toUpperCase()),
                    resultSet.getString("user_position"),
                    resultSet.getDouble("salary"),
                    resultSet.getString("password"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(User user) {
        return List.of(
                new HotelDAO().getId(user.getHotel()),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNum(),
                user.getPassportNum(),
                user.getRole().name().toLowerCase(),
                user.getPosition(),
                user.getSalary(),
                user.getPassword(),
                new java.sql.Date(user.getCreatedAt().getTime()),
                new java.sql.Date(user.getUpdatedAt().getTime())
        );
    }

    public User getUser(int hotelId, String email, String password) {
        String sql = STR."SELECT * FROM \{getTableName()} " +
                         "WHERE hotel_id = ? AND email = ? AND password = ?";
        List<Object> params = List.of(hotelId, email, Objects.requireNonNull(Util.hashPassword(password)));
        return database.getObject(sql, params, this::getObject);
    }

    public List<Object> getPositions() {
        String sql = STR."SELECT DISTINCT user_position FROM \{getTableName()} " +
                         "ORDER BY user_position";
        return database.getList(sql, null);
    }

    public List<Object[]> getUsersByEmailDomain(String emailDomain) {
        String sql = "SELECT * FROM get_users_by_email_domain(?)";
        List<Object> params = List.of(STR."%\{emailDomain}");
        return database.getColumnRows(sql, params);
    }

    public List<Object[]> getHighestPaidEmployeesByPosition(String position) {
        String sql = "SELECT * FROM get_highest_paid_employees_by_position(?)";
        List<Object> params = List.of(Util.capitalize(position.toLowerCase()));
        return database.getColumnRows(sql, params);
    }
}
