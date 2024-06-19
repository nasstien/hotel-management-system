package org.example.hotel.utils.gui;

import org.example.hotel.dao.*;
import org.example.hotel.models.*;

import javafx.scene.control.TableColumn;

import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.Function;

public class Column {
    public static TableColumn<User, ?>[] getUserColumns() {
        int columnCount = Table.getColumnCount(User.class) +
                          Table.getColumnCount(Person.class) +
                          Table.getColumnCount(Entity.class) - 2;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<User, ?>> params = new LinkedHashMap<>() {{
            put("First Name", User::getFirstName);
            put("Last Name", User::getLastName);
            put("Email", User::getEmail);
            put("Phone Number", User::getPhoneNum);
            put("Passport Number", User::getPassportNum);
            put("Role", user -> user.getRole().name().toLowerCase());
            put("Position", User::getPosition);
            put("Salary", User::getSalary);
            put("Created", User::getCreatedAt);
            put("Updated", User::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }

    public static TableColumn<Guest, ?>[] getGuestColumns() {
        int columnCount = Table.getColumnCount(Guest.class) +
                          Table.getColumnCount(Person.class) +
                          Table.getColumnCount(Entity.class) - 1;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<Guest, ?>> params = new LinkedHashMap<>() {{
            put("First Name", Guest::getFirstName);
            put("Last Name", Guest::getLastName);
            put("Email", Guest::getEmail);
            put("Phone Number", Guest::getPhoneNum);
            put("Passport Number", Guest::getPassportNum);
            put("Check-in Date", Guest::getCheckInDate);
            put("Check-out Date", Guest::getCheckOutDate);
            put("Created", Guest::getCreatedAt);
            put("Updated", Guest::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }

    public static TableColumn<Room, ?>[] getRoomColumns() {
        int columnCount = Table.getColumnCount(Room.class) + Table.getColumnCount(Entity.class) - 1;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<Room, ?>> params = new LinkedHashMap<>() {{
            put("Type ID", room -> new RoomTypeDAO().getId(room.getType()));
            put("Payment Method", room -> room.getOccupied() ? "Occupied" : "Available");
            put("Created", Room::getCreatedAt);
            put("Updated", Room::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }

    public static TableColumn<RoomType, ?>[] getRoomTypeColumns() {
        int columnCount = Table.getColumnCount(RoomType.class) + Table.getColumnCount(Entity.class) - 1;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<RoomType, ?>> params = new LinkedHashMap<>() {{
            put("Name", RoomType::getName);
            put("Description", RoomType::getDescription);
            put("Price Per Night", RoomType::getPricePerNight);
            put("Capacity", RoomType::getCapacity);
            put("Created", RoomType::getCreatedAt);
            put("Updated", RoomType::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }


    public static TableColumn<Booking, ?>[] getBookingColumns() {
        int columnCount = Table.getColumnCount(Booking.class) + Table.getColumnCount(Entity.class) - 1;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<Booking, ?>> params = new LinkedHashMap<>() {{
            put("Room ID", booking -> new RoomDAO().getId(booking.getRoom()));
            put("Guest ID", booking -> new GuestDAO().getId(booking.getGuest()));
            put("Payment ID", booking -> new PaymentDAO().getId(booking.getPayment()));
            put("Created", Booking::getCreatedAt);
            put("Updated", Booking::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }

    public static TableColumn<Payment, ?>[] getPaymentColumns() {
        int columnCount = Table.getColumnCount(Payment.class) + Table.getColumnCount(Entity.class) - 1;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<Payment, ?>> params = new LinkedHashMap<>() {{
            put("Guest ID", payment -> new GuestDAO().getId(payment.getGuest()));
            put("Total Sum", Payment::getTotalSum);
            put("Room Charges", Payment::getRoomCharges);
            put("Service Charges", Payment::getServiceCharges);
            put("Payment Method", payment -> payment.getPaymentMethod().name().toLowerCase());
            put("Status", payment -> payment.getPaid() ? "Paid" : "Not Paid");
            put("Created", Payment::getCreatedAt);
            put("Updated", Payment::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }

    public static TableColumn<Service, ?>[] getServiceColumns() {
        int columnCount = Table.getColumnCount(Service.class) + Table.getColumnCount(Entity.class) - 1;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<Service, ?>> params = new LinkedHashMap<>() {{
            put("Name", Service::getName);
            put("Description", Service::getDescription);
            put("Category", Service::getCategory);
            put("Start Time", Service::getStartTime);
            put("End Time", Service::getEndTime);
            put("Price", Service::getPrice);
            put("Status", service -> service.getAvailable() ? "Available" : "Unavailable");
            put("Created", Service::getCreatedAt);
            put("Updated", Service::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }

    public static TableColumn<ServiceOrder, ?>[] getServiceOrderColumns() {
        int columnCount = Table.getColumnCount(ServiceOrder.class) + Table.getColumnCount(Entity.class) - 1;
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, columnCount);
        Map<String, Function<ServiceOrder, ?>> params = new LinkedHashMap<>() {{
            put("Service ID", order -> new ServiceDAO().getId(order.getService()));
            put("Booking ID", order -> new BookingDAO().getId(order.getBooking()));
            put("Payment ID", order -> new PaymentDAO().getId(order.getPayment()));
            put("Service Time", ServiceOrder::getServiceTime);
            put("Service Date", ServiceOrder::getServiceDate);
            put("Created", ServiceOrder::getCreatedAt);
            put("Updated", ServiceOrder::getUpdatedAt);
        }};

        return Table.createColumns(params, cellWidth);
    }

    public static TableColumn<Object[], ?>[] getGuestsByServiceOrderColumns() {
        List<String> columnNames = List.of("Guest ID", "First Name", "Last Name", "Email", "Service ID", "Service Name", "Category", "Price");
        return Table.createColumns(columnNames);
    }

    public static TableColumn<Object[], ?>[] getUsersByEmailDomainColumns() {
        List<String> columnNames = List.of("ID", "First Name", "Last Name", "Email", "Position");
        return Table.createColumns(columnNames);
    }

    public static TableColumn<Object[], ?>[] getPaymentsByDateIntervalColumns() {
        List<String> columnNames = List.of("ID", "Total Sum", "Room Charges", "Service Charges", "Payment Method", "Guest ID");
        return Table.createColumns(columnNames);
    }

    public static TableColumn<Object[], ?>[] getGuestsPerServiceColumns() {
        List<String> columnNames = List.of("Service Name", "Number Of Guests");
        return Table.createColumns(columnNames);
    }

    public static TableColumn<Object[], ?>[] getHighestPaidEmployeesByPositionColumns() {
        List<String> columnNames = List.of("ID", "First Name", "Last Name", "Email", "Phone Number", "Passport Number", "Role", "Position", "Salary");
        return Table.createColumns(columnNames);
    }

    public static TableColumn<Object[], ?>[] getMaxPricePerServiceCategoryColumns() {
        List<String> columnNames = List.of("Category", "Max Price");
        return Table.createColumns(columnNames);
    }

    public static TableColumn<Object[], ?>[] getUnorderedServicesByDateIntervalColumns() {
        List<String> columnNames = List.of("ID", "Name", "Description", "Category", "Start Time", "End Time", "Price");
        return Table.createColumns(columnNames);
    }

    public static TableColumn<Object[], ?>[] getGuestsWithCommentsColumns() {
        List<String> columnNames = List.of("ID", "First Name", "Last Name", "Email", "Comment");
        return Table.createColumns(columnNames);
    }
}
