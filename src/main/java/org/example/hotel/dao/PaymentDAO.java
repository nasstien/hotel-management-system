package org.example.hotel.dao;

import org.example.hotel.enums.PaymentMethod;
import org.example.hotel.models.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.List;

import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.database;

public class PaymentDAO extends BaseDAO<Payment> {
    @Override
    public String getTableName() {
        return "payments";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "hotel_id",
                "guest_id",
                "total_sum",
                "room_charges",
                "service_charges",
                "payment_method",
                "paid",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public Payment getObject(ResultSet resultSet) {
        try {
            return new Payment(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    new GuestDAO().getOne(resultSet.getInt("guest_id")),
                    resultSet.getDouble("room_charges"),
                    resultSet.getDouble("service_charges"),
                    PaymentMethod.valueOf(resultSet.getString("payment_method").toUpperCase()),
                    resultSet.getBoolean("paid"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(Payment payment) {
        return List.of(
                new HotelDAO().getId(payment.getHotel()),
                new GuestDAO().getId(payment.getGuest()),
                payment.getTotalSum(),
                payment.getRoomCharges(),
                payment.getServiceCharges(),
                payment.getPaymentMethod().name().toLowerCase(),
                payment.getPaid(),
                new java.sql.Date(payment.getCreatedAt().getTime()),
                new java.sql.Date(payment.getUpdatedAt().getTime())
        );
    }

    public List<Object[]> getPaymentsByDateInterval(Date startDate, Date endDate) {
        String sql = "SELECT * FROM get_payments_by_date_interval(?, ?, ?)";
        List<Object> params = List.of(
                new HotelDAO().getId(hotel),
                new java.sql.Date(startDate.getTime()),
                new java.sql.Date(endDate.getTime())
        );
        return database.getColumnRows(sql, params);
    }
}
