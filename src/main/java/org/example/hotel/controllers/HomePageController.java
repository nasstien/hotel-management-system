package org.example.hotel.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static org.example.hotel.Application.user;

public class HomePageController {
    @FXML public Label welcomeLabel;
    @FXML public Label positionLabel;

    @FXML
    public void initialize() {
        welcomeLabel.setText(STR."Welcome, \{user.getFirstName()} \{user.getLastName()}!");
        positionLabel.setText(STR."\{user.getPosition()} in the Hotel \"\{user.getHotel().getName()}\"");
    }
}
