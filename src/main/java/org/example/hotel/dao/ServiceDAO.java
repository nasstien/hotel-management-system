package org.example.hotel.dao;

import org.example.hotel.models.Service;
import org.example.hotel.utils.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.database;

public class ServiceDAO extends BaseDAO<Service> {
    @Override
    public String getTableName() {
        return "services";
    }

    @Override
    public List<String> getColumnNames() {
        return List.of(
                "hotel_id",
                "name",
                "description",
                "category",
                "start_time",
                "end_time",
                "price",
                "available",
                "created_at",
                "updated_at"
        );
    }

    @Override
    public Service getObject(ResultSet resultSet) {
        try {
            return new Service(
                    new HotelDAO().getOne(resultSet.getInt("hotel_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("category"),
                    DateUtil.parseTime(resultSet.getTime("start_time")),
                    DateUtil.parseTime(resultSet.getTime("end_time")),
                    resultSet.getDouble("price"),
                    resultSet.getBoolean("available"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getParams(Service service) {
        return List.of(
                new HotelDAO().getId(service.getHotel()),
                service.getName(),
                service.getDescription(),
                service.getCategory(),
                java.sql.Time.valueOf(STR."\{service.getStartTime()}:00"),
                java.sql.Time.valueOf(STR."\{service.getEndTime()}:00"),
                service.getPrice(),
                service.getAvailable(),
                new java.sql.Date(service.getCreatedAt().getTime()),
                new java.sql.Date(service.getUpdatedAt().getTime())
        );
    }

    @Override
    public List<Object> getIds() {
        String sql = STR."SELECT id FROM \{getTableName()} " +
                         "WHERE hotel_id = ? AND available = TRUE " +
                         "ORDER BY id";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getList(sql, params);
    }

    public List<Object> getServiceNames() {
        String sql = STR."SELECT DISTINCT name AS service_name FROM \{getTableName()} " +
                         "ORDER BY name";
        return database.getList(sql, null);
    }

    public List<Object[]> getMaxPricePerServiceCategory() {
        String sql = "SELECT * FROM get_max_price_per_service_category(?) " +
                     "ORDER BY max_price DESC";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getColumnRows(sql, params);
    }

    public List<Object[]> getUnorderedServicesByDateInterval(String interval) {
        String sql = STR."SELECT * FROM get_unordered_services_by_date_interval(?, '1 \{interval}')";
        List<Object> params = List.of(new HotelDAO().getId(hotel));
        return database.getColumnRows(sql, params);
    }
}
