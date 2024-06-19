package org.example.hotel.dao;

import org.example.hotel.models.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class RoomTypeDAO extends BaseDAO<RoomType> {
    @Override
    public String getTableName() {
        return "room_types";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "hotel_id",
                "name",
                "description",
                "price_per_night",
                "capacity",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public RoomType getObject(ResultSet resultSet) {
        try {
            return new RoomType(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price_per_night"),
                    resultSet.getInt("capacity"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(RoomType roomType) {
        return List.of(
                new HotelDAO().getId(roomType.getHotel()),
                roomType.getName(),
                roomType.getDescription(),
                roomType.getPricePerNight(),
                roomType.getCapacity(),
                new java.sql.Date(roomType.getCreatedAt().getTime()),
                new java.sql.Date(roomType.getUpdatedAt().getTime())
        );
    }
}
