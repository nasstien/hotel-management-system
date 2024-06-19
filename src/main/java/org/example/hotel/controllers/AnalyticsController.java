package org.example.hotel.controllers;

import org.example.hotel.dao.*;
import org.example.hotel.utils.*;
import org.example.hotel.utils.gui.*;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Date;
import java.util.List;

import static org.example.hotel.Application.history;
import static org.example.hotel.Application.primaryStage;

public class AnalyticsController {
    @FXML
    private void handleGetGuestsByServiceOrderClick() throws Exception {
        List<Object> serviceNames = new ServiceDAO().getServiceNames();
        ComboBox<Object> comboBox = GUI.createComboBox("Service Name", serviceNames);

        Parent root = GUI.loadPage("analytics/analytics-form.fxml");

        VBox vBox = (VBox) root.lookup("#vBox");
        Label headingLabel = (Label) root.lookup("#headingLabel");
        Button getButton = (Button) root.lookup("#getButton");

        int index = vBox.getChildren().indexOf(getButton);
        vBox.getChildren().add(index, comboBox);

        headingLabel.setText("Get Guests By Service Order");
        getButton.setOnAction((e) -> {
            getGuestsByServiceOrder((String) comboBox.getValue());
            comboBox.setValue(null);
        });

        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleGetUsersByEmailDomainClick() throws Exception {
        TextField textField = GUI.createTextField("Email Domain (@example.com)");
        Parent root = GUI.loadPage("analytics/analytics-form.fxml");

        VBox vBox = (VBox) root.lookup("#vBox");
        Label headingLabel = (Label) root.lookup("#headingLabel");
        Button getButton = (Button) root.lookup("#getButton");

        int index = vBox.getChildren().indexOf(getButton);
        vBox.getChildren().add(index, textField);

        Insets insets = new Insets(0, 475, 0, 475);
        VBox.setMargin(textField, insets);

        headingLabel.setText("Get Users By Email Domain");
        getButton.setOnAction((e) -> {
            getUsersByEmailDomain(textField.getText());
            textField.clear();
        });

        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleGetPaymentsByDateIntervalClick() throws Exception {
        Parent root = GUI.loadPage("analytics/analytics-form.fxml");

        DatePicker startDateField = GUI.createDatePicker("Start Date");
        DatePicker endDateField = GUI.createDatePicker("End Date");

        VBox vBox = (VBox) root.lookup("#vBox");
        Label headingLabel = (Label) root.lookup("#headingLabel");
        Button getButton = (Button) root.lookup("#getButton");

        int index = vBox.getChildren().indexOf(getButton);
        vBox.getChildren().add(index, startDateField);
        vBox.getChildren().add(index + 1, endDateField);

        headingLabel.setText("Get Payments By Date Interval");
        getButton.setOnAction((e) -> {
            getPaymentsByDateInterval(Util.parseDate(startDateField.getValue()), Util.parseDate(endDateField.getValue()));
            startDateField.setValue(null);
            endDateField.setValue(null);
        });

        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleGetHotelWeekIncomeClick() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, GUI.SMALL_WINDOW_WIDTH, GUI.SMALL_WINDOW_HEIGHT);
        Stage stage = GUI.createStage(scene, GUI.WINDOW_TITLE, GUI.WINDOW_ICON, null);
        GUI.loadStyleSheet(scene);

        double income = new HotelDAO().getHotelWeekIncome();

        Label headingLabel = GUI.createLabel("Hotel Week Income", "large-text");
        Label incomeLabel = GUI.createLabel(STR."\{income} UAH", "income-label");

        Button closeButton = new Button("Close");
        closeButton.setOnAction((e) -> stage.close());

        vBox.getChildren().addAll(headingLabel, incomeLabel, closeButton);
        stage.show();
    }

    @FXML
    private void handleGetGuestsPerServiceClick() throws Exception {
        String heading = "Number Of Guests Per Service";
        TableColumn<Object[], ?>[] columns = Column.getGuestsPerServiceColumns();
        List<Object[]> rows = new GuestDAO().getGuestsPerService();

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No guests found.");
            return;
        }

        Parent root = loadTable(heading, columns, rows);
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleGetHighestPaidEmployeesByPositionClick() throws Exception {
        List<Object> positions = new UserDAO().getPositions();
        ComboBox<Object> comboBox = GUI.createComboBox("Position", positions);

        Parent root = GUI.loadPage("analytics/analytics-form.fxml");

        VBox vBox = (VBox) root.lookup("#vBox");
        Label headingLabel = (Label) root.lookup("#headingLabel");
        Button getButton = (Button) root.lookup("#getButton");

        if (vBox != null && headingLabel != null && getButton != null) {
            int index = vBox.getChildren().indexOf(getButton);

            vBox.getChildren().add(index, comboBox);
            headingLabel.setText("Highest Paid Employees By Position");
            getButton.setOnAction((e) -> {
                getHighestPaidEmployeesByPosition((String) comboBox.getValue());
                comboBox.setValue(null);
            });
        }

        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleGetMaxPricePerServiceCategoryClick() throws Exception {
        String heading = "Max Price Per Service Category";
        TableColumn<Object[], ?>[] columns = Column.getMaxPricePerServiceCategoryColumns();
        List<Object[]> rows = new ServiceDAO().getMaxPricePerServiceCategory();

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No services found.");
            return;
        }

        Parent root = loadTable(heading, columns, rows);
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleGetUnorderedServicesByDateIntervalClick() throws Exception {
        List<String> intervals = List.of("Year", "Month", "Week", "Day");
        ComboBox<String> comboBox = GUI.createComboBox("Interval", intervals);

        Parent root = GUI.loadPage("analytics/analytics-form.fxml");

        VBox vBox = (VBox) root.lookup("#vBox");
        Label headingLabel = (Label) root.lookup("#headingLabel");
        Button getButton = (Button) root.lookup("#getButton");

        getButton.setOnAction((e) -> getUnorderedServicesByDateInterval(comboBox.getValue().toLowerCase()));

        int index = vBox.getChildren().indexOf(getButton);
        vBox.getChildren().add(index, comboBox);

        headingLabel.setText("Get Unordered Services By Date Interval");
        getButton.setOnAction((e) -> {
            getUnorderedServicesByDateInterval(comboBox.getValue());
            comboBox.setValue(null);
        });

        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleGetGuestsWithCommentsClick() throws Exception {
        String heading = "Guests with comments";
        TableColumn<Object[], ?>[] columns = Column.getGuestsWithCommentsColumns();
        List<Object[]> rows = new GuestDAO().getGuestsWithComments();

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No guests found.");
            return;
        }

        Parent root = loadTable(heading, columns, rows);
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    private void handleBackClick() {
        history.pop();
    }

    private void getGuestsByServiceOrder(String serviceName) {
        if (serviceName == null) {
            Message.displayErrorDialog("Please select a service name.");
            return;
        }

        String heading = STR."Guests who ordered the service \"\{Util.capitalize(serviceName.toLowerCase())}\"";
        TableColumn<Object[], ?>[] columns = Column.getGuestsByServiceOrderColumns();
        List<Object[]> rows = new GuestDAO().getGuestsByServiceOrder(serviceName);

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No guests found.\nPlease select a different service name and try again.");
            return;
        }

        try {
            Parent root = loadTable(heading, columns, rows);
            history.push(GUI.createScene(root));
            primaryStage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void getUsersByEmailDomain(String emailDomain) {
        if (emailDomain.isEmpty()) {
            Message.displayErrorDialog("Please enter an email domain.");
            return;
        }

        String heading = STR."Users with the email domain \"\{emailDomain}\"";
        TableColumn<Object[], ?>[] columns = Column.getUsersByEmailDomainColumns();
        List<Object[]> rows = new UserDAO().getUsersByEmailDomain(emailDomain);

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No users found.\nPlease enter a different email domain and try again.");
            return;
        }

        try {
            Parent root = loadTable(heading, columns, rows);
            history.push(GUI.createScene(root));
            primaryStage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void getPaymentsByDateInterval(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            Message.displayErrorDialog("Please select the start date and end date of the interval.");
            return;
        }

        String heading = STR."Payments made between \{Util.parseDate(startDate)} and \{Util.parseDate(endDate)}";
        TableColumn<Object[], ?>[] columns = Column.getPaymentsByDateIntervalColumns();
        List<Object[]> rows = new PaymentDAO().getPaymentsByDateInterval(startDate, endDate);

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No payments found.\nPlease select a different date interval and try again.");
            return;
        }

        try {
            Parent root = loadTable(heading, columns, rows);
            history.push(GUI.createScene(root));
            primaryStage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void getHighestPaidEmployeesByPosition(String position) {
        if (position == null) {
            Message.displayErrorDialog("Please select a position.");
            return;
        }

        String heading = STR."Employees who earn more than all employees of the position \"\{Util.capitalize(position.toLowerCase())}\"";
        TableColumn<Object[], ?>[] columns = Column.getHighestPaidEmployeesByPositionColumns();
        List<Object[]> rows = new UserDAO().getHighestPaidEmployeesByPosition(position);

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No employees found.");
            return;
        }

        try {
            Parent root = loadTable(heading, columns, rows);
            history.push(GUI.createScene(root));
            primaryStage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void getUnorderedServicesByDateInterval(String interval) {
        if (interval == null) {
            Message.displayErrorDialog("Please select an interval.");
            return;
        }

        interval = interval.toLowerCase();
        String heading = STR."Services that have not been ordered in a \{Util.capitalize(interval)}";
        TableColumn<Object[], ?>[] columns = Column.getUnorderedServicesByDateIntervalColumns();
        List<Object[]> rows = new ServiceDAO().getUnorderedServicesByDateInterval(interval);

        if (rows.isEmpty()) {
            Message.displayErrorDialog("No services found.");
            return;
        }

        try {
            Parent root = loadTable(heading, columns, rows);
            history.push(GUI.createScene(root));
            primaryStage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private <T> Parent loadTable(String heading, TableColumn<Object[], ?>[] columns, List<T> rows) throws Exception {
        Parent root = GUI.loadPage("analytics/analytics-table.fxml");
        Label headingLabel = (Label) root.lookup("#headingLabel");
        VBox vBox = (VBox) root.lookup("#vBox");

        headingLabel.setText(heading);

        int tableViewIndex = vBox.getChildren().indexOf(root.lookup("#tableView"));
        TableView<T> tableView = (TableView<T>) vBox.getChildren().get(tableViewIndex);

        if (tableView != null) {
            tableView.getItems().addAll(rows);
            tableView.getColumns().addAll((TableColumn[]) columns);
            tableView.setPrefSize(Table.TABLE_WIDTH, Table.TABLE_HEIGHT);
        }

        return root;
    }
}
