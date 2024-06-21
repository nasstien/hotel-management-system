package org.example.hotel.controllers;

import org.example.hotel.dao.*;
import org.example.hotel.models.*;
import org.example.hotel.utils.gui.*;
import org.example.hotel.utils.HotkeyHandler;
import org.example.hotel.interfaces.EntityController;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import static org.example.hotel.Application.history;
import static org.example.hotel.utils.gui.GUI.initializeHandler;

public class GetAllPageController<T> implements EntityController {
    private String entity;
    private Map<String, Runnable> handlers;
    private List<T> rows;
    private TableColumn<?, ?>[] columns;

    @FXML private VBox root;
    @FXML private Label headingLabel;
    @FXML private TableView<T> tableView;

    @FXML
    public void initialize(String entity) throws Exception {
        this.entity = entity;
        this.handlers = new HashMap<>();
        this.rows = new ArrayList<>();
        this.columns = new TableColumn[] {};

        initializeHotkeys();
        initializeHandlers();
        initializeHandler(entity, handlers);
        initializeTable();

        headingLabel.setText(STR."Get All \{this.entity}s");
    }

    private void initializeHandlers() {
        GUI.initializeHandlers(
                handlers,
                this::getAllUsers,
                this::getAllGuests,
                this::getAllRooms,
                this::getAllRoomTypes,
                this::getAllBookings,
                this::getAllPayments,
                this::getAllServices,
                this::getAllServiceOrders);
    }

    private void initializeHotkeys() {
        HotkeyHandler.handleBackPressed(root);
        HotkeyHandler.handleExitPressed(root);
    }

    private void initializeTable() {
        if (!rows.isEmpty() && columns.length != 0) {
            tableView.getItems().addAll(rows);
            tableView.getColumns().addAll((TableColumn<T, ?>[]) columns);
            tableView.setPrefSize(Table.TABLE_WIDTH, Table.TABLE_HEIGHT);
        }
    }

    private void getAllUsers() {
        List<User> users = new UserDAO().getAll();

        if (users.isEmpty()) {
            Message.displayErrorDialog("No users found.");
            return;
        }

        rows = (List<T>) users;
        columns = Column.getUserColumns();
    }

    private void getAllGuests() {
        List<Guest> guests = new GuestDAO().getAll();

        if (guests.isEmpty()) {
            Message.displayErrorDialog("No guests found.");
            return;
        }

        rows = (List<T>) guests;
        columns = Column.getGuestColumns();
    }

    private void getAllRooms() {
        List<Room> rooms = new RoomDAO().getAll();

        if (rooms.isEmpty()) {
            Message.displayErrorDialog("No rooms found.");
            return;
        }

        rows = (List<T>) rooms;
        columns = Column.getRoomColumns();
    }

    private void getAllRoomTypes() {
        List<RoomType> roomTypes = new RoomTypeDAO().getAll();

        if (roomTypes.isEmpty()) {
            Message.displayErrorDialog("No room types found.");
            return;
        }

        rows = (List<T>) roomTypes;
        columns = Column.getRoomTypeColumns();
    }

    private void getAllBookings() {
        List<Booking> bookings = new BookingDAO().getAll();

        if (bookings.isEmpty()) {
            Message.displayErrorDialog("No bookings found.");
            return;
        }

        rows = (List<T>) bookings;
        columns = Column.getBookingColumns();
    }

    private void getAllPayments() {
        List<Payment> payments = new PaymentDAO().getAll();

        if (payments.isEmpty()) {
            Message.displayErrorDialog("No payments found.");
            return;
        }

        rows = (List<T>) payments;
        columns = Column.getPaymentColumns();
    }

    private void getAllServices() {
        List<Service> services = new ServiceDAO().getAll();

        if (services.isEmpty()) {
            Message.displayErrorDialog("No services found.");
            return;
        }

        rows = (List<T>) services;
        columns = Column.getServiceColumns();
    }

    private void getAllServiceOrders() {
        List<ServiceOrder> serviceOrders = new ServiceOrderDAO().getAll();

        if (serviceOrders.isEmpty()) {
            Message.displayErrorDialog("No service orders found.");
            return;
        }

        rows = (List<T>) serviceOrders;
        columns = Column.getServiceOrderColumns();
    }

    @FXML
    private void handleBackClick() {
        history.pop();
    }
}
