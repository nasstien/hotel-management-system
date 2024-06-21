package org.example.hotel.controllers;

import org.example.hotel.dao.*;
import org.example.hotel.models.*;
import org.example.hotel.utils.*;
import org.example.hotel.utils.gui.*;
import org.example.hotel.interfaces.EntityController;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import static org.example.hotel.Application.history;

public class GetOnePageController implements EntityController {
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

        headingLabel.setText(STR."Get \{this.entity}");
        idField.setPromptText(STR."\{this.entity} ID");
        idField.getItems().addAll(Util.getIds(this.entity));
    }

    @FXML
    private void handleGetClick() {
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
                this::getUser,
                this::getGuest,
                this::getRoom,
                this::getRoomType,
                this::getBooking,
                this::getPayment,
                this::getService,
                this::getServiceOrder);
    }

    private void initializeHotkeys() {
        HotkeyHandler.handleEnterPressed(root, this::handleGetClick);
        HotkeyHandler.handleBackPressed(root);
        HotkeyHandler.handleExitPressed(root);
    }

    private void getUser() {
        User user = new UserDAO().getOne(id);
        List<String> lines = List.of(
                STR."First Name: \{user.getFirstName()}",
                STR."Last Name: \{user.getLastName()}",
                STR."Email: \{user.getEmail()}",
                STR."Phone Number: \{user.getPhoneNum()}",
                STR."Passport Number: \{user.getPassportNum()}",
                STR."Role: \{Util.capitalize(user.getRole().name().toLowerCase())}",
                STR."Position: \{user.getPosition()}",
                STR."Salary: \{user.getSalary()} UAH per month",
                STR."Created: \{user.getCreatedAt()}",
                STR."Updated: \{user.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void getGuest() {
        Guest guest = new GuestDAO().getOne(id);
        List<String> lines = List.of(
                STR."First Name: \{guest.getFirstName()}",
                STR."Last Name: \{guest.getLastName()}",
                STR."Email: \{guest.getEmail()}",
                STR."Phone Number: \{guest.getPhoneNum()}",
                STR."Passport Number: \{guest.getPassportNum()}",
                STR."Check-in Date: \{guest.getCheckInDate()}",
                STR."Check-out Date: \{guest.getCheckOutDate()}",
                STR."Created: \{guest.getCreatedAt()}",
                STR."Updated: \{guest.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void getRoom() {
        Room room = new RoomDAO().getOne(id);
        int capacity = room.getType().getCapacity();
        List<String> lines = List.of(
                STR."Type ID: \{new RoomTypeDAO().getId(room.getType())}",
                STR."Price: \{room.getType().getPricePerNight()} UAH/night",
                STR."The room can accommodate \{capacity} \{capacity == 1 ? "person" : "people"}",
                STR."\{room.getOccupied() ? "The room is occupied" : "The room is available" }",
                STR."Created: \{room.getCreatedAt()}",
                STR."Updated: \{room.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void getRoomType() {
        RoomType roomType = new RoomTypeDAO().getOne(id);
        int capacity = roomType.getCapacity();
        List<String> lines = List.of(
                STR."Type Name: \{roomType.getName()}",
                STR."Description: \{roomType.getDescription()}",
                STR."Price: \{roomType.getPricePerNight()} UAH/night",
                STR."The room type can accommodate \{capacity} \{capacity == 1 ? "person" : "people"}",
                STR."Created: \{roomType.getCreatedAt()}",
                STR."Updated: \{roomType.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void getBooking() {
        Booking booking = new BookingDAO().getOne(id);
        List<String> lines = List.of(
                STR."Room ID: \{new RoomDAO().getId(booking.getRoom())}",
                STR."Guest ID: \{new GuestDAO().getId(booking.getGuest())}",
                STR."Payment ID: \{new PaymentDAO().getId(booking.getPayment())}",
                STR."Created: \{booking.getCreatedAt()}",
                STR."Updated: \{booking.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void getPayment() {
        Payment payment = new PaymentDAO().getOne(id);
        List<String> lines = List.of(
                STR."Guest ID: \{new GuestDAO().getId(payment.getGuest())}",
                STR."Total Sum: \{payment.getTotalSum()} UAH",
                STR."Room Charges: \{payment.getRoomCharges()} UAH",
                STR."Service Charges: \{payment.getServiceCharges()} UAH",
                STR."Payment Method: \{Util.capitalize(payment.getPaymentMethod().name().toLowerCase())}",
                STR."Status: \{payment.getPaid() ? "Payment has been made" : "Payment has not been made"}",
                STR."Created: \{payment.getCreatedAt()}",
                STR."Updated: \{payment.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void getService() {
        Service service = new ServiceDAO().getOne(id);
        List<String> lines = List.of(
                STR."Service Name: \{service.getName()}",
                STR."Description: \{service.getDescription()}",
                STR."Category: \{service.getCategory()}",
                STR."Operating Hours: \{service.getStartTime()} - \{service.getEndTime()}",
                STR."Price: \{service.getPrice()} UAH/hour",
                STR."Status: \{service.getAvailable() ? "The service is available" : "The service is unavailable"}",
                STR."Created: \{service.getCreatedAt()}",
                STR."Updated: \{service.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void getServiceOrder() {
        ServiceOrder order = new ServiceOrderDAO().getOne(id);
        List<String> lines = List.of(
                STR."Service ID: \{new ServiceDAO().getId(order.getService())}",
                STR."Booking ID: \{new BookingDAO().getId(order.getBooking())}",
                STR."Payment ID: \{new PaymentDAO().getId(order.getPayment())}",
                STR."Service Time: \{order.getServiceTime()}",
                STR."Service Date: \{order.getServiceDate()}",
                STR."Created: \{order.getCreatedAt()}",
                STR."Updated: \{order.getUpdatedAt()}"
        );

        createWindow(lines);
    }

    private void createWindow(List<String> lines) {
        idField.setValue(null);

        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(vBox, GUI.SMALL_WINDOW_WIDTH, GUI.SMALL_WINDOW_HEIGHT);
        Stage stage = GUI.createStage(scene, GUI.WINDOW_TITLE, GUI.WINDOW_ICON, null);
        GUI.loadStyleSheet(scene);

        List<Label> labels = new ArrayList<>();
        for (String line : lines) {
            labels.add(GUI.createLabel(line, "line-label"));
        }

        Button closeButton = new Button("Close");
        closeButton.setOnAction((e) -> stage.close());

        vBox.getChildren().addAll(labels);
        vBox.getChildren().add(closeButton);
        VBox.setMargin(closeButton, new Insets(20, 20, 20, 20));
        stage.show();
    }

    @FXML
    private void handleBackClick() {
        history.pop();
    }
}
