package org.example.hotel.dao;

import org.example.hotel.models.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class BookingDAO extends BaseDAO<Booking> {
    @Override
    public String getTableName() {
        return "bookings";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "hotel_id",
                "room_id",
                "guest_id",
                "payment_id",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public Booking getObject(ResultSet resultSet) {
        try {
            return new Booking(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    new RoomDAO().getOne(resultSet.getInt("room_id")),
                    new GuestDAO().getOne(resultSet.getInt("guest_id")),
                    new PaymentDAO().getOne(resultSet.getInt("payment_id")),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(Booking booking) {
        return List.of(
                new HotelDAO().getId(booking.getHotel()),
                new RoomDAO().getId(booking.getRoom()),
                new GuestDAO().getId(booking.getGuest()),
                new PaymentDAO().getId(booking.getPayment()),
                new java.sql.Date(booking.getCreatedAt().getTime()),
                new java.sql.Date(booking.getUpdatedAt().getTime())
        );
    }
}
