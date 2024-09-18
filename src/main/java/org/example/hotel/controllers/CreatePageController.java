package org.example.hotel.controllers;

import org.example.hotel.dao.*;
import org.example.hotel.models.*;
import org.example.hotel.enums.*;
import org.example.hotel.utils.*;
import org.example.hotel.utils.gui.*;
import org.example.hotel.interfaces.EntityController;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.fxml.FXML;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.history;

public class CreatePageController implements EntityController {
    private String entity;
    private Map<String, Runnable> handlers;
    private Parent form;

    @FXML private VBox root;
    @FXML private Label headingLabel;
    @FXML private Button createButton;

    @FXML
    public void initialize(String entity) throws Exception {
        this.entity = entity;
        this.handlers = new HashMap<>();

        initializeHandlers();
        initializeHotkeys();

        headingLabel.setText(STR."Create \{this.entity}");
        loadForm();
        GUI.initializeComboBoxes(form);

    }

    @FXML
    private void handleCreateClick() {
        GUI.initializeHandler(entity, handlers);
    }

    private void initializeHandlers() {
        GUI.initializeHandlers(
                handlers,
                this::createUser,
                this::createGuest,
                this::createRoom,
                this::createRoomType,
                this::createBooking,
                this::createPayment,
                this::createService,
                this::createServiceOrder);
    }

    private void initializeHotkeys() {
        HotkeyHandler.handleEnterPressed(root, this::handleCreateClick);
        HotkeyHandler.handleBackPressed(root);
        HotkeyHandler.handleExitPressed(root);
    }

    private void loadForm() throws Exception {
        String fileName = entity.replace(' ', '-').toLowerCase();
        form = GUI.loadPage(STR."forms/\{fileName}.fxml");

        int buttonIndex = root.getChildren().indexOf(createButton);
        root.getChildren().add(buttonIndex, form);
    }

    private void createUser() {
        ComboBox<String> roleField = (ComboBox<String>) form.lookup("#roleField");
        TextField firstNameField = (TextField) form.lookup("#firstNameField");
        TextField lastNameField = (TextField) form.lookup("#lastNameField");
        TextField emailField = (TextField) form.lookup("#emailField");
        TextField phoneNumField = (TextField) form.lookup("#phoneNumField");
        TextField passportNumField = (TextField) form.lookup("#passportNumField");
        TextField positionField = (TextField) form.lookup("#positionField");
        TextField salaryField = (TextField) form.lookup("#salaryField");
        TextField passwordField = (TextField) form.lookup("#passwordField");
        TextField passwordConfirmField = (TextField) form.lookup("#passwordConfirmField");

        String errMessage = "";

        if (Validator.isEmptyFields(new TextField[] {
                firstNameField,
                lastNameField,
                emailField,
                phoneNumField,
                passportNumField,
                positionField,
                salaryField,
                passwordField,
                passwordConfirmField}) ||
                roleField.getValue() == null) {
            errMessage = "There are empty fields.\nPlease fill in all fields and try again.";
        } else if (!Validator.isValidEmail(emailField.getText())) {
            errMessage = "Email is invalid.";
        } else if (!Validator.isValidPhoneNum(phoneNumField.getText())) {
            errMessage = "Phone number must start with \"+\" and contain only integers.";
        } else if (!Validator.isDouble(salaryField.getText())) {
            errMessage = "Salary must be a double.";
        } else if (!Validator.isValidPassword(passwordField.getText())) {
            errMessage = "Password must be at least 8 characters long and include a combination of letters, numbers, and special characters.";
        } else if (passwordConfirmField.getText().isEmpty()) {
            errMessage = "Please confirm password.";
        } else if (!passwordField.getText().equals(passwordConfirmField.getText())) {
            errMessage = "Password and password confirmation fields are different.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        User user = new User(
                hotel,
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText().toLowerCase(),
                phoneNumField.getText(),
                passportNumField.getText(),
                Role.valueOf(roleField.getValue().toUpperCase()),
                StringUtil.capitalize(positionField.getText().toLowerCase()),
                Double.parseDouble(salaryField.getText()),
                DatabaseUtil.hashPassword(passwordField.getText()),
                new Date(), new Date());
        new UserDAO().insert(user);

        Message.displaySuccessDialog("User has been successfully created.");
        history.pop();
    }

    private void createGuest() {
        TextField firstNameField = (TextField) form.lookup("#firstNameField");
        TextField lastNameField = (TextField) form.lookup("#lastNameField");
        TextField emailField = (TextField) form.lookup("#emailField");
        TextField phoneNumField = (TextField) form.lookup("#phoneNumField");
        TextField passportNumField = (TextField) form.lookup("#passportNumField");
        DatePicker checkInDateField = (DatePicker) form.lookup("#checkInDateField");
        DatePicker checkOutDateField = (DatePicker) form.lookup("#checkOutDateField");

        String errMessage = "";

        if (Validator.isEmptyFields(new TextField[] {firstNameField, lastNameField, emailField, phoneNumField, passportNumField}) ||
                checkInDateField.getValue() == null ||
                checkOutDateField.getValue() == null) {
            errMessage = "There are empty fields.\nPlease fill in all fields and try again.";
        } else if (!Validator.isValidEmail(emailField.getText())) {
            errMessage = "Email is invalid.";
        } else if (!Validator.isValidPhoneNum(phoneNumField.getText())) {
            errMessage = "Phone number must start with \"+\" and contain only integers.";
        } else if (!Validator.isValidDateInterval(DateUtil.parseDate(checkInDateField.getValue()), DateUtil.parseDate(checkOutDateField.getValue()))) {
            errMessage = "Check-out date cannot be earlier than check-in date.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        Guest guest = new Guest(
                hotel,
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                phoneNumField.getText(),
                passportNumField.getText(),
                DateUtil.parseDate(checkInDateField.getValue()),
                DateUtil.parseDate(checkOutDateField.getValue()),
                new Date(), new Date());
        new GuestDAO().insert(guest);

        Message.displaySuccessDialog("Guest has been successfully created.");
        history.pop();
    }

    private void createRoom() {
        ComboBox<Integer> typeIdField = (ComboBox) form.lookup("#typeIdField");

        if (typeIdField.getValue() == null) {
            Message.displayErrorDialog("Please select a room type ID.");
            return;
        }

        Room room = new Room(hotel, new RoomTypeDAO().getOne(typeIdField.getValue()), false, new Date(), new Date());
        new RoomDAO().insert(room);

        Message.displaySuccessDialog("Room has been successfully created.");
        history.pop();
    }

    private void createRoomType() {
        TextField nameField = (TextField) form.lookup("#nameField");
        TextArea descriptionField = (TextArea) form.lookup("#descriptionField");
        TextField priceField = (TextField) form.lookup("#priceField");
        TextField capacityField = (TextField) form.lookup("#capacityField");

        String errMessage = "";

        if (Validator.isEmptyFields(new TextField[] {nameField, priceField, capacityField})) {
            errMessage = "There are empty fields.\nPlease fill in all fields and try again.";
        } else if (!Validator.isDouble(priceField.getText())) {
            errMessage = "Price must be a double.";
        } else if (Double.parseDouble(priceField.getText()) <= 0) {
            errMessage = "Price must be greater than 0.";
        } else if (!Validator.isInteger(capacityField.getText())) {
            errMessage = "Capacity must be an integer.";
        } else if (Integer.parseInt(capacityField.getText()) <= 0) {
            errMessage = "Capacity must be greater than 0.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        double price = Double.parseDouble(priceField.getText());
        int capacity = Integer.parseInt(capacityField.getText());
        RoomType roomType = new RoomType(
                hotel,
                StringUtil.capitalize(nameField.getText()),
                descriptionField.getText(),
                price, capacity,
                new Date(), new Date()
        );
        new RoomTypeDAO().insert(roomType);

        Message.displaySuccessDialog("Room type has been successfully created.");
        history.pop();
    }

    private void createBooking() {
        ComboBox<Integer> roomIdField = (ComboBox<Integer>) form.lookup("#roomIdField");
        ComboBox<Integer> guestIdField = (ComboBox<Integer>) form.lookup("#guestIdField");
        ComboBox<Integer> paymentIdField = (ComboBox<Integer>) form.lookup("#paymentIdField");

        String errMessage = "";

        if (roomIdField.getValue() == null || guestIdField.getValue() == null || paymentIdField.getValue() == null) {
            errMessage = "There are empty fields.\nPlease fill in all fields and try again.";
        } else if (new RoomDAO().getOne(roomIdField.getValue()).getOccupied()) {
            errMessage = "Room is occupied.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        Room room = new RoomDAO().getOne(roomIdField.getValue());
        Guest guest = new GuestDAO().getOne(guestIdField.getValue());
        Payment payment = new PaymentDAO().getOne(paymentIdField.getValue());

        Booking booking = new Booking(hotel, room, guest, payment, new Date(), new Date());
        new BookingDAO().insert(booking);

        Room updatedRoom = new Room(room);
        updatedRoom.setOccupied(true);
        new RoomDAO().update(new RoomDAO().getId(room), updatedRoom);

        Message.displaySuccessDialog("Booking has been successfully created.");
        history.pop();
    }

    private void createPayment() {
        ComboBox<Integer> guestIdField = (ComboBox<Integer>) form.lookup("#guestIdField");
        ComboBox<Integer> roomIdField = (ComboBox<Integer>) form.lookup("#roomIdField");
        ComboBox<Integer> serviceIdField = (ComboBox<Integer>) form.lookup("#serviceIdField");
        ComboBox<String> methodField = (ComboBox<String>) form.lookup("#paymentMethodField");
        CheckBox isPaidCheckBox = (CheckBox) form.lookup("#isPaidCheckBox");

        if (methodField.getValue() == null) {
            Message.displayErrorDialog("Please select a payment method.");
            return;
        }

        Guest guest = new GuestDAO().getOne(guestIdField.getValue());
        Room room = new RoomDAO().getOne(roomIdField.getValue());
        Service service = serviceIdField.getValue() != null ? new ServiceDAO().getOne(serviceIdField.getValue()) : null;

        Double roomCharges = room.getType().getPricePerNight() * DateUtil.getNumOfDays(guest.getCheckInDate(), guest.getCheckOutDate());
        Double serviceCharges = service != null ? service.getPrice() : 0;
        PaymentMethod paymentMethod = PaymentMethod.valueOf(methodField.getValue().toUpperCase());

        Payment payment = new Payment(
                hotel, guest, roomCharges, serviceCharges, paymentMethod,
                isPaidCheckBox.isSelected(),
                new Date(), new Date()
        );
        new PaymentDAO().insert(payment);

        Message.displaySuccessDialog("Payment has been successfully created.");
        history.pop();
    }

    private void createService() {
        TextField nameField = (TextField) form.lookup("#nameField");
        TextArea descriptionField = (TextArea) form.lookup("#descriptionField");
        TextField categoryField = (TextField) form.lookup("#categoryField");
        TextField startTimeField = (TextField) form.lookup("#startTimeField");
        TextField endTimeField = (TextField) form.lookup("#endTimeField");
        TextField priceField = (TextField) form.lookup("#priceField");
        CheckBox isAvailableCheckBox = (CheckBox) form.lookup("#isAvailableCheckBox");

        String errMessage = "";

        if (Validator.isEmptyFields(new TextField[] {
                nameField,
                categoryField,
                startTimeField,
                endTimeField,
                priceField})) {
            errMessage = "There are empty fields.\nPlease fill in all fields and try again.";
        } else if (!Validator.isDouble(priceField.getText())) {
            errMessage = "Price must be a double.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        Service service = new Service(
                hotel,
                nameField.getText(),
                descriptionField.getText(),
                categoryField.getText(),
                startTimeField.getText(),
                endTimeField.getText(),
                Double.parseDouble(priceField.getText()),
                isAvailableCheckBox.isSelected(),
                new Date(), new Date()
        );
        new ServiceDAO().insert(service);

        Message.displaySuccessDialog("Service has been successfully created.");
        history.pop();
    }

    private void createServiceOrder() {
        ComboBox<Integer> serviceIdField = (ComboBox<Integer>) form.lookup("#serviceIdField");
        ComboBox<Integer> bookingIdField = (ComboBox<Integer>) form.lookup("#bookingIdField");
        ComboBox<Integer> paymentIdField = (ComboBox<Integer>) form.lookup("#paymentIdField");
        DatePicker serviceDateField = (DatePicker) form.lookup("#serviceDateField");
        TextField serviceTimeField = (TextField) form.lookup("#serviceTimeField");

        String errMessage = "";

        if (serviceIdField.getValue() == null ||
                bookingIdField.getValue() == null ||
                paymentIdField.getValue() == null ||
                serviceDateField.getValue() == null ||
                serviceTimeField.getText().isEmpty()) {
            errMessage = "There are empty fields.\nPlease fill in all fields and try again.";
        } else if (!Validator.isValidTime(serviceTimeField.getText())) {
            errMessage = "Invalid time format.\nExpected format: HH:MM.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        Service service = new ServiceDAO().getOne(serviceIdField.getValue());
        Booking booking = new BookingDAO().getOne(bookingIdField.getValue());
        Payment payment = new PaymentDAO().getOne(paymentIdField.getValue());

        ServiceOrder order = new ServiceOrder(
                hotel, service, booking, payment,
                serviceTimeField.getText(),
                DateUtil.parseDate(serviceDateField.getValue()),
                new Date(), new Date()
        );
        new ServiceOrderDAO().insert(order);

        Message.displaySuccessDialog("Order has been successfully created.");
        history.pop();
    }

    @FXML
    private void handleBackClick() {
        history.pop();
    }
}
