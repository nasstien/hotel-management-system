package org.example.hotel.dao;

import org.example.hotel.models.Hotel;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.database;

public class HotelDAO extends BaseDAO<Hotel> {
    @Override
    public String getTableName() {
        return "hotels";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "name",
                "address",
                "contact_num",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public Hotel getObject(ResultSet resultSet) {
        try {
            return new Hotel(
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("contact_num"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(Hotel hotel) {
        return List.of(
                hotel.getName(),
                hotel.getAddress(),
                hotel.getContactNum(),
                new java.sql.Date(hotel.getCreatedAt().getTime()),
                new java.sql.Date(hotel.getUpdatedAt().getTime())
        );
    }

    @Override
    public Hotel getOne(int id) {
        String sql = STR."SELECT * FROM \{getTableName()} " +
                         "WHERE id = ?";
        List<Object> params = List.of(id);
        return database.getObject(sql, params, this::getObject);
    }

    @Override
    public List<Object> getIds() {
        String sql = STR."SELECT id FROM \{getTableName()} " +
                         "ORDER BY id";
        return database.getList(sql, null);
    }

    public Double getHotelWeekIncome() {
        String sql = "SELECT * FROM get_hotel_week_income(?)";
        List<Object> params = List.of(getId(hotel));
        Object weekIncome = database.getSingleValue(sql, params, "week_income");
        return weekIncome != null ? new BigDecimal(String.valueOf(weekIncome)).doubleValue() : 0.0;
    }
}
