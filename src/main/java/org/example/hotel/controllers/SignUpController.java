package org.example.hotel.controllers;

import org.example.hotel.dao.HotelDAO;
import org.example.hotel.dao.UserDAO;
import org.example.hotel.models.User;
import org.example.hotel.enums.Role;
import org.example.hotel.utils.Util;
import org.example.hotel.utils.Validator;
import org.example.hotel.utils.gui.Message;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import java.util.Date;
import java.util.Objects;

import static org.example.hotel.Application.user;
import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.primaryStage;
import static org.example.hotel.Application.mainMenu;
import static org.example.hotel.Application.login;

public class SignUpController {
    @FXML private ComboBox<Object> hotelIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumField;
    @FXML private TextField passportNumField;
    @FXML private TextField positionField;
    @FXML private TextField passwordField;
    @FXML private TextField passwordConfirmField;

    @FXML
    public void initialize() {
        hotelIdField.getItems().addAll(new HotelDAO().getIds());
    }

    @FXML
    private void handleSignUpClick() throws Exception {
        String errMessage = "";

        if (Validator.isEmptyFields(new TextField[] {
                firstNameField,
                lastNameField,
                emailField,
                phoneNumField,
                passportNumField,
                positionField,
                passwordField,
                passportNumField}) ||
                hotelIdField.getValue() == null) {
            errMessage = "There are empty fields.\nPlease fill in all fields and try again.";
        } else if (!Validator.isValidEmail(emailField.getText())) {
            errMessage = "Email is invalid.";
        } else if (!Validator.isValidPhoneNum(phoneNumField.getText())) {
            errMessage = "Phone number must start with \"+\" and contain only integers.";
        } else if (!Validator.isValidPassword(passwordField.getText())) {
            errMessage = "Password must be at least 8 characters long and include a combination of letters, numbers, and special characters.";
        } else if (!Objects.equals(passwordField.getText(), passwordConfirmField.getText())) {
            errMessage = "Password and password confirmation fields are different.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        hotel = new HotelDAO().getOne((int) hotelIdField.getValue());
        Role defaultRole = Role.EMPLOYEE;
        Double defaultSalary = 0.0;

        User newUser = new User(
                hotel,
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                phoneNumField.getText(),
                passportNumField.getText(),
                defaultRole,
                positionField.getText(),
                defaultSalary,
                Util.hashPassword(passwordField.getText()),
                new Date(),
                new Date()
        );
        new UserDAO().insert(newUser);
        user = newUser;

        primaryStage.setScene(mainMenu());
    }

    @FXML
    private void handleLogInClick() throws Exception {
        primaryStage.setScene(login());
    }
}
