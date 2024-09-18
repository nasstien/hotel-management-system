package org.example.hotel.dao;

import org.example.hotel.models.ServiceOrder;
import org.example.hotel.utils.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class ServiceOrderDAO extends BaseDAO<ServiceOrder> {
    @Override
    public String getTableName() {
        return "service_orders";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "hotel_id",
                "service_id",
                "booking_id",
                "payment_id",
                "service_time",
                "service_date",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public ServiceOrder getObject(ResultSet resultSet) {
        try {
            return new ServiceOrder(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    new ServiceDAO().getOne(resultSet.getInt("service_id")),
                    new BookingDAO().getOne(resultSet.getInt("booking_id")),
                    new PaymentDAO().getOne(resultSet.getInt("payment_id")),
                    DateUtil.parseTime(resultSet.getTime("service_time")),
                    resultSet.getDate("service_date"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(ServiceOrder order) {
        return List.of(
                new HotelDAO().getId(order.getHotel()),
                new ServiceDAO().getId(order.getService()),
                new BookingDAO().getId(order.getBooking()),
                new PaymentDAO().getId(order.getPayment()),
                java.sql.Time.valueOf(STR."\{order.getServiceTime()}:00"),
                new java.sql.Date(order.getServiceDate().getTime()),
                new java.sql.Date(order.getCreatedAt().getTime()),
                new java.sql.Date(order.getUpdatedAt().getTime())
        );
    }
}
