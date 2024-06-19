package org.example.hotel.dao;

import org.example.hotel.models.Guest;
import org.example.hotel.models.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static org.example.hotel.Application.database;
import static org.example.hotel.Application.hotel;

public class RoomDAO extends BaseDAO<Room> {
    @Override
    public String getTableName() {
        return "rooms";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "hotel_id",
                "type_id",
                "occupied",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public Room getObject(ResultSet resultSet) {
        try {
            return new Room(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    new RoomTypeDAO().getOne(resultSet.getInt("type_id")),
                    resultSet.getBoolean("occupied"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(Room room) {
        return List.of(
                new HotelDAO().getId(room.getHotel()),
                new RoomTypeDAO().getId(room.getType()),
                room.getOccupied(),
                new java.sql.Date(room.getCreatedAt().getTime()),
                new java.sql.Date(room.getUpdatedAt().getTime())
        );
    }

    @Override
    public List<Object> getIds() {
        String sql = STR."SELECT id FROM \{getTableName()} " +
                         "WHERE hotel_id = ? AND occupied = FALSE " +
                         "ORDER BY id";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getList(sql, params);
    }

    public Room getOneByGuest(Guest guest) {
        String sql = "SELECT * FROM rooms r " +
                     "JOIN bookings b ON b.room_id = r.id " +
                     "WHERE guest_id = ?";
        List<Object> params = List.of(new GuestDAO().getId(guest));
        return database.getObject(sql, params, this::getObject);
    }
}
