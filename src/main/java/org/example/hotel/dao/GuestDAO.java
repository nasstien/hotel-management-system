package org.example.hotel.dao;

import org.example.hotel.models.Guest;
import org.example.hotel.utils.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.database;

public class GuestDAO extends BaseDAO<Guest> {
    @Override
    public String getTableName() {
        return "guests";
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
                "check_in_date",
                "check_out_date",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public Guest getObject(ResultSet resultSet) {
        try {
            return new Guest(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_num"),
                    resultSet.getString("passport_num"),
                    resultSet.getDate("check_in_date"),
                    resultSet.getDate("check_out_date"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(Guest guest) {
        return List.of(
                new HotelDAO().getId(guest.getHotel()),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmail(),
                guest.getPhoneNum(),
                guest.getPassportNum(),
                new java.sql.Date(guest.getCheckInDate().getTime()),
                new java.sql.Date(guest.getCheckOutDate().getTime()),
                new java.sql.Date(guest.getCreatedAt().getTime()),
                new java.sql.Date(guest.getUpdatedAt().getTime())
        );
    }

    public List<Object[]> getGuestsByServiceOrder(String serviceName) {
        String sql = "SELECT * FROM get_guests_by_service_order(?, ?)";
        List<Object> params = List.of(
                new HotelDAO().getId(hotel),
                StringUtil.capitalize(serviceName.toLowerCase())
        );
        return database.getColumnRows(sql, params);
    }

    public List<Object[]> getGuestsPerService() {
        String sql = "SELECT * FROM get_guests_per_service(?)" +
                     "ORDER BY guest_count DESC";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getColumnRows(sql, params);
    }

    public List<Object[]> getGuestsWithComments() {
        String sql = "SELECT * FROM get_guests_with_comments(?)";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getColumnRows(sql, params);
    }
}
