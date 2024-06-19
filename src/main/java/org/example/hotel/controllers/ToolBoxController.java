package org.example.hotel.controllers;

import org.example.hotel.models.*;
import org.example.hotel.utils.Util;
import org.example.hotel.utils.gui.GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.util.List;

import static org.example.hotel.Application.history;
import static org.example.hotel.Application.database;
import static org.example.hotel.Application.primaryStage;

public class ToolBoxController {
    @FXML private VBox vBox;
    @FXML private VBox vBoxAdmin;

    @FXML
    private void handleAnalyticsClick() throws Exception {
        GUI.loadMenu("analytics/analytics.fxml", vBoxAdmin);
    }

    @FXML
    private void handleUsersClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBoxAdmin, List.of(User.class.getSimpleName()));
    }

    @FXML
    private void handleGuestsClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBox, List.of(Guest.class.getSimpleName()));
    }

    @FXML
    private void handleRoomsClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBox, List.of(Room.class.getSimpleName()));
    }

    @FXML
    private void handleRoomTypesClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBox, List.of(Util.addSpaces(RoomType.class.getSimpleName())));
    }

    @FXML
    private void handleBookingsClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBox, List.of(Booking.class.getSimpleName()));
    }

    @FXML
    private void handlePaymentsClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBox, List.of(Payment.class.getSimpleName()));
    }

    @FXML
    private void handleServicesClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBox, List.of(Service.class.getSimpleName()));
    }

    @FXML
    private void handleServiceOrdersClick() throws Exception {
        GUI.loadMenu("control-panel/control-panel.fxml", vBox, List.of(Util.addSpaces(ServiceOrder.class.getSimpleName())));
    }

    @FXML
    private void handleSettingsClick() throws Exception {
        Parent root = GUI.loadPage("settings.fxml");
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleExitClick() {
        database.close();
        Platform.exit();
    }
}
