package org.example.hotel.controllers;

import org.example.hotel.models.User;
import org.example.hotel.dao.UserDAO;
import org.example.hotel.dao.HotelDAO;
import org.example.hotel.utils.Validator;
import org.example.hotel.utils.gui.Message;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import static org.example.hotel.Application.user;
import static org.example.hotel.Application.hotel;
import static org.example.hotel.Application.primaryStage;
import static org.example.hotel.Application.mainMenu;
import static org.example.hotel.Application.signup;

public class LogInController {
    @FXML private ComboBox<Object> hotelIdField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    @FXML
    public void initialize() {
        hotelIdField.getItems().addAll(new HotelDAO().getIds());
    }

    @FXML
    private void handleLogInClick() throws Exception {
        if (Validator.isEmptyFields(new TextField[] {emailField, passwordField}) || hotelIdField.getValue() == null) {
            Message.displayErrorDialog("There are empty fields.\nPlease fill in all fields and try again.");
            return;
        }

        hotel = new HotelDAO().getOne((int) hotelIdField.getValue());
        User currentUser = new UserDAO().getUser((int) hotelIdField.getValue(), emailField.getText(), passwordField.getText());

        if (currentUser == null) {
            Message.displayErrorDialog("Incorrect Hotel ID, email or password.");
            return;
        }

        user = currentUser;
        primaryStage.setScene(mainMenu());
    }

    @FXML
    private void handleSignUpClick() throws Exception {
        primaryStage.setScene(signup());
    }
}
