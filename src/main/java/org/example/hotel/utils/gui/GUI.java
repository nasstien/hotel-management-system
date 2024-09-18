package org.example.hotel.utils.gui;

import org.example.hotel.dao.*;
import org.example.hotel.models.*;
import org.example.hotel.interfaces.EntityController;
import org.example.hotel.utils.StringUtil;

import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.LinkedHashMap;
import java.util.function.Supplier;

public class GUI {
    public static final String WINDOW_TITLE = "Hotel Management System";
    public static final String WINDOW_ICON = "icon.png";

    public static final int LARGE_WINDOW_WIDTH = 1200;
    public static final int LARGE_WINDOW_HEIGHT = 700;

    public static final int SMALL_WINDOW_WIDTH = 600;
    public static final int SMALL_WINDOW_HEIGHT = 400;

    public static Parent loadPage(String fxmlPath) throws Exception {
        return loadPage(fxmlPath, null);
    }

    public static Parent loadPage(String fxmlPath, List<Object> params) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(GUI.class.getResource(STR."/org/example/hotel/views/\{fxmlPath}")));
        Parent parent = fxmlLoader.load();

        if(params != null) {
            Object controller = fxmlLoader.getController();
            if (controller instanceof EntityController) {
                ((EntityController) controller).initialize((String) params.getFirst());
            }
        }

        return parent;
    }

    public static void loadMenu(String fxmlPath, VBox vBox) throws Exception {
        loadMenu(fxmlPath, vBox, null);
    }

    public static void loadMenu(String fxmlPath, VBox vBox, List<Object> params) throws Exception {
        Parent parent = vBox;
        while (parent != null && !(parent instanceof GridPane)) {
            parent = parent.getParent();
        }

        GridPane gridPane = (GridPane) parent;
        if (gridPane != null && !gridPane.getChildren().isEmpty()) {
            Parent root = (params == null) ? GUI.loadPage(fxmlPath, null) : GUI.loadPage(fxmlPath, params);

            gridPane.getChildren().removeLast();
            gridPane.add(root, 1, 0);
        }
    }

    public static void loadStyleSheet(Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(GUI.class.getResource("/org/example/hotel/styles.css")).toExternalForm());
    }

    public static Stage createStage(Scene scene, String title, String iconImage, EventHandler<WindowEvent> onClose) {
        String imagePath = STR."/org/example/hotel/icons/\{iconImage}";
        Stage stage = new Stage();

        stage.setOnCloseRequest(onClose);
        stage.getIcons().add(new Image(Objects.requireNonNull(GUI.class.getResourceAsStream(imagePath))));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);

        return stage;
    }

    public static Scene createScene(Parent root) {
        return new Scene(root, LARGE_WINDOW_WIDTH, LARGE_WINDOW_HEIGHT);
    }

    public static Alert createAlert(Alert.AlertType type, String iconImage, String title, String contentText) {
        String imagePath = STR."/org/example/hotel/icons/message/\{iconImage}";
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(Message.class.getResource(imagePath)).toExternalForm()));

        ImageView icon = new ImageView(new Image(Objects.requireNonNull(Message.class.getResource(imagePath)).toExternalForm()));
        alert.setGraphic(icon);

        return alert;
    }

    public static Label createLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    public static TextField createTextField(String promptText) {
        TextField textField = new TextField();

        textField.setPromptText(promptText);
        textField.setFocusTraversable(false);

        return textField;
    }

    public static DatePicker createDatePicker(String promptText) {
        DatePicker datePicker = new DatePicker();

        datePicker.setPromptText(promptText);
        datePicker.setFocusTraversable(false);

        return datePicker;
    }

    public static <T> ComboBox<T> createComboBox(String promptText, List<T> items) {
        ComboBox<T> comboBox = new ComboBox<>();

        comboBox.setPromptText(promptText);
        comboBox.getItems().addAll(items);
        comboBox.setFocusTraversable(false);

        return comboBox;
    }

    public static void initializeHandler(String entity, Map<String, Runnable> handlers) {
        Runnable action = handlers.get(entity);

        if (action != null) {
            action.run();
            return;
        }

        throw new IllegalArgumentException(STR."Unknown entity: \{entity}");
    }

    public static void initializeHandlers(
            Map<String, Runnable> handlers,
            Runnable handleUsers,
            Runnable handleGuests,
            Runnable handleRooms,
            Runnable handleRoomTypes,
            Runnable handleBookings,
            Runnable handlePayments,
            Runnable handleServices,
            Runnable handleOrderedServices) {
        handlers.put(User.class.getSimpleName(), handleUsers);
        handlers.put(Guest.class.getSimpleName(), handleGuests);
        handlers.put(Room.class.getSimpleName(), handleRooms);
        handlers.put(StringUtil.addSpaces(RoomType.class.getSimpleName()), handleRoomTypes);
        handlers.put(Booking.class.getSimpleName(), handleBookings);
        handlers.put(Payment.class.getSimpleName(), handlePayments);
        handlers.put(Service.class.getSimpleName(), handleServices);
        handlers.put(StringUtil.addSpaces(ServiceOrder.class.getSimpleName()), handleOrderedServices);
    }

    public static void initializeComboBoxes(Parent root) {
        Map<String, Supplier<Iterable<?>>> data = new LinkedHashMap<>() {{
            put("#guestIdField", () -> new GuestDAO().getIds());
            put("#typeIdField", () -> new RoomTypeDAO().getIds());
            put("#roomIdField", () -> new RoomDAO().getIds());
            put("#paymentIdField", () -> new PaymentDAO().getIds());
            put("#bookingIdField", () -> new BookingDAO().getIds());
            put("#serviceIdField", () -> new ServiceDAO().getIds());
            put("#roleField", StringUtil::getUserRoles);
            put("#paymentMethodField", StringUtil::getPaymentMethods);
        }};

        data.forEach((id, action) -> populateComboBox(root, id, action));
    }

    private static <T> void populateComboBox(Parent root, String comboBoxId, Supplier<Iterable<?>> action) {
        ComboBox<T> comboBox = (ComboBox<T>) root.lookup(comboBoxId);
        if (comboBox != null) {
            Iterable<?> items = action.get();
            for (Object item : items) {
                comboBox.getItems().add((T) item);
            }
        }
    }
}
