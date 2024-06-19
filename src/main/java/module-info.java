module org.example.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires dotenv.java;

    exports org.example.hotel;
    exports org.example.hotel.enums;
    exports org.example.hotel.models;
    exports org.example.hotel.interfaces;
    exports org.example.hotel.utils;
    exports org.example.hotel.utils.gui;

    opens org.example.hotel to javafx.fxml;
    opens org.example.hotel.controllers to javafx.fxml;
    opens org.example.hotel.utils to javafx.fxml;
    opens org.example.hotel.utils.gui to javafx.fxml;
}