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

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.example.hotel.Application.history;

public class EditPageController implements EntityController {
    private int id;
    private String entity;
    private Map<String, Runnable> handlers;
    private Parent form;

    @FXML private VBox root;
    @FXML private Label headingLabel;
    @FXML private ComboBox<Object> idField;
    @FXML private Button saveButton;

    @FXML
    public void initialize(String entity) throws Exception {
        this.entity = entity;
        this.handlers = new HashMap<>();

        initializeHandlers();
        initializeHotkeys();

        headingLabel.setText(STR."Edit \{this.entity}");
        idField.setPromptText(STR."\{this.entity} ID");
        idField.getItems().addAll(Util.getIds(this.entity));

        loadForm();
        GUI.initializeComboBoxes(form);
    }

    @FXML
    private void handleSaveClick() {
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
                this::saveUser,
                this::saveGuest,
                this::saveRoom,
                this::saveRoomType,
                this::saveBooking,
                this::savePayment,
                this::saveService,
                this::saveServiceOrder);
    }

    private void initializeHotkeys() {
        HotkeyHandler.handleEnterPressed(root, this::handleSaveClick);
        HotkeyHandler.handleBackPressed(root);
        HotkeyHandler.handleExitPressed(root);
    }

    private void loadForm() throws Exception {
        String fileName = entity.replace(' ', '-').toLowerCase();
        form = GUI.loadPage(STR."forms/\{fileName}.fxml");

        int buttonIndex = root.getChildren().indexOf(saveButton);
        root.getChildren().add(buttonIndex, form);
    }

    private void saveUser() {
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

        if (!emailField.getText().isEmpty() && !Validator.isValidEmail(emailField.getText())) {
            errMessage = "Email is invalid.";
        } else if (!phoneNumField.getText().isEmpty() && !Validator.isValidPhoneNum(phoneNumField.getText())) {
            errMessage = "Phone number must start with \"+\" and contain only integers.";
        } else if (!salaryField.getText().isEmpty() && !Validator.isDouble(salaryField.getText())) {
            errMessage = "Salary must be a double.";
        } else if (!passwordField.getText().isEmpty() && !Validator.isValidPassword(passwordField.getText())) {
            errMessage = "Password must be at least 8 characters long and include a combination of letters, numbers, and special characters.";
        } else if (!passportNumField.getText().isEmpty() && passwordConfirmField.getText().isEmpty()) {
            errMessage = "Please confirm password.";
        } else if (!passwordField.getText().isEmpty() &&
                !passwordConfirmField.getText().isEmpty() &&
                !passwordField.getText().equals(passwordConfirmField.getText())) {
            errMessage = "Password and password confirmation fields are different.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        boolean modified = false;
        User user = new UserDAO().getOne(id);

        modified |= Util.updateValue(firstNameField, user::setFirstName);
        modified |= Util.updateValue(lastNameField, user::setLastName);
        modified |= Util.updateValue(emailField, user::setEmail);
        modified |= Util.updateValue(phoneNumField, user::setPhoneNum);
        modified |= Util.updateValue(passportNumField, user::setPassportNum);
        modified |= Util.updateValue(roleField, role -> user.setRole(Role.valueOf(role)));
        modified |= Util.updateValue(positionField, user::setPosition);
        modified |= Util.updateValue(salaryField, salary -> user.setSalary(Double.parseDouble(salary)));
        modified |= Util.updateValue(passwordField, user::setPassword);

        if (modified) {
            user.setUpdatedAt(new Date());
            new UserDAO().update(id, user);
            Message.displaySuccessDialog("User has been successfully updated.");
        } else {
            Message.displayInfoDialog("User has not been modified.");
        }

        history.pop();
    }

    private void saveGuest() {
        TextField firstNameField = (TextField) form.lookup("#firstNameField");
        TextField lastNameField = (TextField) form.lookup("#lastNameField");
        TextField emailField = (TextField) form.lookup("#emailField");
        TextField phoneNumField = (TextField) form.lookup("#phoneNumField");
        TextField passportNumField = (TextField) form.lookup("#passportNumField");
        DatePicker checkInDateField = (DatePicker) form.lookup("#checkInDateField");
        DatePicker checkOutDateField = (DatePicker) form.lookup("#checkOutDateField");

        String errMessage = "";

         if (!emailField.getText().isEmpty() && !Validator.isValidEmail(emailField.getText())) {
            errMessage = "Email is invalid.";
        } else if (!phoneNumField.getText().isEmpty() && Validator.isValidPhoneNum(phoneNumField.getText())) {
            errMessage = "Phone number must start with \"+\" and contain only integers.";
        } else if (checkInDateField.getValue() != null && checkOutDateField.getValue() != null &&
                Validator.isValidDateInterval(Util.parseDate(checkInDateField.getValue()), Util.parseDate(checkOutDateField.getValue()))) {
            errMessage = "Check-out date cannot be earlier than check-in date.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        boolean modified = false;
        Guest guest = new GuestDAO().getOne(id);

        modified |= Util.updateValue(firstNameField, guest::setFirstName);
        modified |= Util.updateValue(lastNameField, guest::setLastName);
        modified |= Util.updateValue(emailField, guest::setEmail);
        modified |= Util.updateValue(phoneNumField, guest::setPhoneNum);
        modified |= Util.updateValue(passportNumField, guest::setPassportNum);

        modified |= Util.updateValue(checkInDateField, date -> {
            Date checkInDate = Util.parseDate(LocalDate.parse(date));
            guest.setCheckInDate(checkInDate);
        });

        modified |= Util.updateValue(checkOutDateField, date -> {
            Date checkOutDate = Util.parseDate(LocalDate.parse(date));
            guest.setCheckOutDate(checkOutDate);
        });

        if (modified) {
            guest.setUpdatedAt(new Date());
            new GuestDAO().update(id, guest);
            Message.displaySuccessDialog("Guest has been successfully updated.");
        } else {
            Message.displayInfoDialog("Guest has not been modified.");
        }

        history.pop();
    }

    private void saveRoom() {
        ComboBox<Integer> typeIdField = (ComboBox<Integer>) form.lookup("#typeIdField");

        boolean modified = false;
        Room room = new RoomDAO().getOne(id);

        modified |= Util.updateValue(typeIdField, typeId -> {
            RoomType type = new RoomTypeDAO().getOne(Integer.parseInt(typeId));
            room.setType(type);
        });

        if (modified) {
            room.setUpdatedAt(new Date());
            new RoomDAO().update(id, room);
            Message.displaySuccessDialog("Room has been successfully updated.");
        } else {
            Message.displayInfoDialog("Room has not been modified.");
        }

        history.pop();
    }

    private void saveRoomType() {
        TextField nameField = (TextField) form.lookup("#nameField");
        TextArea descriptionField = (TextArea) form.lookup("#descriptionField");
        TextField priceField = (TextField) form.lookup("#priceField");
        TextField capacityField = (TextField) form.lookup("#capacityField");

        String errMessage = "";

        if (!priceField.getText().isEmpty() && !Validator.isDouble(priceField.getText())) {
            errMessage = "Price must be a double.";
        } else if (!priceField.getText().isEmpty() && Double.parseDouble(priceField.getText()) <= 0) {
            errMessage = "Price must be greater than 0.";
        } else if (!capacityField.getText().isEmpty() && !Validator.isInteger(capacityField.getText())) {
            errMessage = "Capacity must be an integer.";
        } else if (!capacityField.getText().isEmpty() && Integer.parseInt(capacityField.getText()) <= 0) {
            errMessage = "Capacity must be greater than 0.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        boolean modified = false;
        RoomType roomType = new RoomTypeDAO().getOne(id);

        modified |= Util.updateValue(nameField, roomType::setName);
        modified |= Util.updateValue(descriptionField, roomType::setDescription);
        modified |= Util.updateValue(priceField, price -> roomType.setPricePerNight(Double.parseDouble(price)));
        modified |= Util.updateValue(capacityField, capacity -> roomType.setCapacity(Integer.parseInt(capacity)));

        if (modified) {
            roomType.setUpdatedAt(new Date());
            new RoomTypeDAO().update(id, roomType);
            Message.displaySuccessDialog("Room type has been successfully updated.");
        } else {
            Message.displayInfoDialog("Room type has not been modified.");
        }

        history.pop();
    }

    private void saveBooking() {
        ComboBox<Integer> roomIdField = (ComboBox<Integer>) form.lookup("#roomIdField");
        ComboBox<Integer> guestIdField = (ComboBox<Integer>) form.lookup("#guestIdField");
        ComboBox<Integer> paymentIdField = (ComboBox<Integer>) form.lookup("#paymentIdField");

        if (roomIdField.getValue() != null && new RoomDAO().getOne(roomIdField.getValue()).getOccupied()) {
            Message.displayErrorDialog("Room is occupied.");
        }

        boolean modified = false;
        Booking booking = new BookingDAO().getOne(id);

        modified |= Util.updateValue(roomIdField, roomId -> {
            Room room = new RoomDAO().getOne(Integer.parseInt(roomId));
            booking.setRoom(room);
        });

        modified |= Util.updateValue(guestIdField, guestId -> {
            Guest guest = new GuestDAO().getOne(Integer.parseInt(guestId));
            booking.setGuest(guest);
        });

        modified |= Util.updateValue(paymentIdField, paymentId -> {
            Payment payment = new PaymentDAO().getOne(Integer.parseInt(paymentId));
            booking.setPayment(payment);
        });

        if (modified) {
            booking.setUpdatedAt(new Date());
            new BookingDAO().update(id, booking);
            Message.displaySuccessDialog("Booking has been successfully updated.");
        } else {
            Message.displayInfoDialog("Booking has not been modified.");
        }

        history.pop();
    }

    private void savePayment() {
        ComboBox<Integer> guestIdField = (ComboBox<Integer>) form.lookup("#guestIdField");
        ComboBox<Integer> roomIdField = (ComboBox<Integer>) form.lookup("#roomIdField");
        ComboBox<Integer> serviceIdField = (ComboBox<Integer>) form.lookup("#serviceIdField");
        ComboBox<String> methodField = (ComboBox<String>) form.lookup("#paymentMethodField");
        CheckBox paidCheckBox = (CheckBox) form.lookup("#isPaidCheckBox");

        boolean modified = false;
        Payment payment = new PaymentDAO().getOne(id);

        paidCheckBox.setOnAction((e) -> {
            payment.setPaid(paidCheckBox.isSelected());
        });

        modified |= Util.updateValue(guestIdField, guestId -> {
            Guest guest = new GuestDAO().getOne(Integer.parseInt(guestId));
            payment.setGuest(guest);
        });

        modified |= Util.updateValue(roomIdField, roomId -> {
            Room room = new RoomDAO().getOne(Integer.parseInt(roomId));
            payment.setRoomCharges(room.getType().getPricePerNight());
        });

        modified |= Util.updateValue(serviceIdField, serviceId -> {
            Service service = new ServiceDAO().getOne(Integer.parseInt(serviceId));
            payment.setServiceCharges(service.getPrice());
        });

        modified |= Util.updateValue(methodField, method -> {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(method.toUpperCase());
            payment.setPaymentMethod(paymentMethod);
        });

        if (modified) {
            payment.setTotalSum(payment.calcTotalSum());
            payment.setUpdatedAt(new Date());
            new PaymentDAO().update(id, payment);
            Message.displaySuccessDialog("Payment has been successfully updated.");
        } else {
            Message.displayInfoDialog("Payment has not been modified.");
        }

        history.pop();
    }

    private void saveService() {
        TextField nameField = (TextField) form.lookup("#nameField");
        TextArea descriptionField = (TextArea) form.lookup("#descriptionField");
        TextField categoryField = (TextField) form.lookup("#categoryField");
        TextField startTimeField = (TextField) form.lookup("#startTimeField");
        TextField endTimeField = (TextField) form.lookup("#endTimeField");
        TextField priceField = (TextField) form.lookup("#priceField");
        CheckBox isAvailableCheckBox = (CheckBox) form.lookup("#isAvailableCheckBox");

        if (!priceField.getText().isEmpty() && !Validator.isDouble(priceField.getText())) {
            Message.displayErrorDialog("Price must be a double.");
            return;
        }

        boolean modified = false;
        Service service = new ServiceDAO().getOne(id);

        isAvailableCheckBox.setOnAction((e) -> {
            service.setAvailable(isAvailableCheckBox.isSelected());
        });

        modified |= Util.updateValue(nameField, service::setName);
        modified |= Util.updateValue(descriptionField, service::setDescription);
        modified |= Util.updateValue(categoryField, service::setCategory);
        modified |= Util.updateValue(startTimeField, service::setStartTime);
        modified |= Util.updateValue(endTimeField, service::setEndTime);
        modified |= Util.updateValue(priceField, price -> service.setPrice(Double.parseDouble(price)));

        if (modified) {
            service.setUpdatedAt(new Date());
            new ServiceDAO().update(id, service);
            Message.displaySuccessDialog("Service has been successfully updated.");
        } else {
            Message.displayInfoDialog("Service has not been modified.");
        }

        history.pop();
    }

    private void saveServiceOrder() {
        ComboBox<Integer> serviceIdField = (ComboBox<Integer>) form.lookup("#serviceIdField");
        ComboBox<Integer> bookingIdField = (ComboBox<Integer>) form.lookup("#bookingIdField");
        ComboBox<Integer> paymentIdField = (ComboBox<Integer>) form.lookup("#paymentIdField");
        DatePicker serviceDateField = (DatePicker) form.lookup("#serviceDateField");
        TextField serviceTimeField = (TextField) form.lookup("#serviceTimeField");

        if (!serviceTimeField.getText().isEmpty() && !Validator.isValidTime(serviceTimeField.getText())) {
            Message.displayErrorDialog("Invalid time format.\nExpected format: HH:MM.");
        }

        boolean modified = false;
        ServiceOrder order = new ServiceOrderDAO().getOne(id);

        modified |= Util.updateValue(serviceIdField, serviceId -> {
            Service service = new ServiceDAO().getOne(Integer.parseInt(serviceId));
            order.setService(service);
        });

        modified |= Util.updateValue(bookingIdField, bookingId -> {
            Booking booking = new BookingDAO().getOne(Integer.parseInt(bookingId));
            order.setBooking(booking);
        });

        modified |= Util.updateValue(paymentIdField, paymentId -> {
            Payment payment = new PaymentDAO().getOne(Integer.parseInt(paymentId));
            order.setPayment(payment);
        });

        modified |= Util.updateValue(serviceTimeField, order::setServiceTime);
        modified |= Util.updateValue(serviceDateField, date -> {
            Date serviceDate = Util.parseDate(LocalDate.parse(date));
            order.setServiceDate(serviceDate);
        });

        if (modified) {
            order.setUpdatedAt(new Date());
            new ServiceOrderDAO().update(id, order);
            Message.displaySuccessDialog("Order has been successfully updated.");
        } else {
            Message.displayInfoDialog("Order has not been modified.");
        }

        history.pop();
    }

    @FXML
    private void handleBackClick() {
        history.pop();
    }
}
