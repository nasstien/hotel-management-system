package org.example.hotel.controllers;

import org.example.hotel.dao.*;
import org.example.hotel.utils.*;
import org.example.hotel.utils.gui.*;
import org.example.hotel.interfaces.EntityController;
import org.example.hotel.models.Booking;
import org.example.hotel.models.Guest;
import org.example.hotel.models.Room;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

import java.util.Map;
import java.util.HashMap;

import static org.example.hotel.Application.history;

public class DeletePageController implements EntityController {
    private int id;
    private String entity;
    private Map<String, Runnable> handlers;

    @FXML private VBox root;
    @FXML private Label headingLabel;
    @FXML private ComboBox<Object> idField;

    @FXML
    public void initialize(String entity) {
        this.entity = entity;
        this.handlers = new HashMap<>();

        initializeHandlers();
        initializeHotkeys();

        headingLabel.setText(STR."Delete \{this.entity}");
        idField.setPromptText(STR."\{this.entity} ID");
        idField.getItems().addAll(Util.getIds(this.entity));
    }

    @FXML
    private void handleDeleteClick() {
        if (idField.getValue() == null) {
            Message.displayErrorDialog("ID is not provided.\nPlease select an ID and try again.");
            return;
        }

        this.id = (int) idField.getValue();
        GUI.initializeHandler(entity, handlers);
    }

    private void initializeHandlers() {
        GUI.initializeHandlers(
                handlers,
                this::deleteUser,
                this::deleteGuest,
                this::deleteRoom,
                this::deleteRoomType,
                this::deleteBooking,
                this::deletePayment,
                this::deleteService,
                this::deleteServiceOrder);
    }

    private void initializeHotkeys() {
        HotkeyHandler.handleEnterPressed(root, this::handleDeleteClick);
        HotkeyHandler.handleBackPressed(root);
        HotkeyHandler.handleExitPressed(root);
    }

    private void deleteUser() {
        String message = "Are you sure you want to delete the user?";
        Message.displayConfirmDialog(message, () -> {
            new UserDAO().delete(id);
            Message.displaySuccessDialog("User has been successfully deleted.");
            history.pop();
        });
    }

    private void deleteGuest() {
        String message = "Are you sure you want to delete the guest?\nAll associated data will also be deleted.";
        Message.displayConfirmDialog(message, () -> {
            Guest guest = new GuestDAO().getOne(id);
            Room room = new RoomDAO().getOneByGuest(guest);
            Room updatedRoom = new Room(room);

            updatedRoom.setOccupied(false);
            new RoomDAO().update(new RoomDAO().getId(room), updatedRoom);

            new GuestDAO().delete(id);
            Message.displaySuccessDialog("Guest has been successfully deleted.");
            history.pop();
        });
    }

    private void deleteRoom() {
        String message = "Are you sure you want to delete the room?\nAll associated data will also be deleted.";
        Message.displayConfirmDialog(message, () -> {
            new RoomDAO().delete(id);
            Message.displaySuccessDialog("Room has been successfully deleted.");
            history.pop();
        });
    }

    private void deleteRoomType() {
        String message = "Are you sure you want to delete the room type?\nAll associated data will also be deleted.";
        Message.displayConfirmDialog(message, () -> {
            new RoomTypeDAO().delete(id);
            Message.displaySuccessDialog("Room type has been successfully deleted.");
            history.pop();
        });
    }

    private void deleteBooking() {
        String message = "Are you sure you want to delete the booking?\nAll associated data will also be deleted.";
        Message.displayConfirmDialog(message, () -> {
            Booking booking = new BookingDAO().getOne(id);
            Room room = new RoomDAO().getOneByGuest(booking.getGuest());
            Room updatedRoom = new Room(room);

            updatedRoom.setOccupied(false);
            new RoomDAO().update(new RoomDAO().getId(room), updatedRoom);

            new BookingDAO().delete(id);
            Message.displaySuccessDialog("Booking has been successfully deleted.");
            history.pop();
        });
    }

    private void deletePayment() {
        String message = "Are you sure you want to delete the payment?\nAll associated data will also be deleted.";
        Message.displayConfirmDialog(message, () -> {
            new PaymentDAO().delete(id);
            Message.displaySuccessDialog("Payment has been successfully deleted.");
            history.pop();
        });
    }

    private void deleteService() {
        String message = "Are you sure you want to delete the service?\nAll associated data will also be deleted.";
        Message.displayConfirmDialog(message, () -> {
            new ServiceDAO().delete(id);
            Message.displaySuccessDialog("Service has been successfully deleted.");
            history.pop();
        });
    }

    private void deleteServiceOrder() {
        String message = "Are you sure you want to delete the order?";
        Message.displayConfirmDialog(message, () -> {
            new ServiceOrderDAO().delete(id);
            Message.displaySuccessDialog("Order has been successfully deleted.");
            history.pop();
        });
    }

    @FXML
    private void handleBackClick() {
        history.pop();
    }
}
