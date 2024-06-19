package org.example.hotel.utils;

import org.example.hotel.dao.*;
import org.example.hotel.enums.*;

import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.time.LocalDate;
import java.time.ZoneId;

public class Util {
    public static String addSpaces(String str) {
        return str.replaceAll("([A-Z])", " $1").trim();
    }

    public static String capitalize(String str) {
        StringBuilder capitalized = new StringBuilder(str.length());
        boolean capitalizeNext = true;

        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                capitalized.append(c);
            } else if (capitalizeNext) {
                capitalized.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                capitalized.append(c);
            }
        }

        return capitalized.toString();
    }

    public static String parseDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Date parseDate(LocalDate date) {
        if (date != null) {
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        return null;
    }

    public static String parseTime(Time time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

    public static int getNumOfDays(Date startDate, Date endDate) {
        long difference = Math.abs(endDate.getTime() - startDate.getTime());
        return (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

    public static void setParams(PreparedStatement statement, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
    }

    public static String joinStrings(String separator, List<String> strings, int trimLength) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            builder.append(strings.get(i));

            if (i < strings.size() - 1) {
                builder.append(separator);
            } else {
                builder.append(separator, 0, separator.length() - trimLength);
            }
        }

        return builder.toString();
    }

    public static List<Object> getIds(String entity) {
        switch (entity) {
            case "User": return new UserDAO().getIds();
            case "Guest": return new GuestDAO().getIds();
            case "Room": return new RoomDAO().getIds();
            case "Room Type": return new RoomTypeDAO().getIds();
            case "Booking": return new BookingDAO().getIds();
            case "Payment": return new PaymentDAO().getIds();
            case "Service": return new ServiceDAO().getIds();
            case "Service Order": return new ServiceOrderDAO().getIds();
            default: return null;
        }
    }

    public static List<String> getUserRoles() {
        List<String> roles = new ArrayList<>();

        for (Role role : Role.values()) {
            roles.add(capitalize(role.name().toLowerCase()));
        }

        return roles;
    }

    public static List<String> getPaymentMethods() {
        List<String> paymentMethods = new ArrayList<>();

        for (PaymentMethod method : PaymentMethod.values()) {
            paymentMethods.add(capitalize(method.name().toLowerCase()));
        }

        return paymentMethods;
    }

    public static boolean updateValue(TextField field, Consumer<String> update) {
        String input = field.getText();
        Optional<String> value = Optional.ofNullable(input)
                                         .filter(t -> !t.isEmpty());
        value.ifPresent(update);
        return value.isPresent();
    }

    public static boolean updateValue(TextArea field, Consumer<String> update) {
        String input = field.getText();
        Optional<String> value = Optional.ofNullable(input)
                                         .filter(t -> !t.isEmpty());
        value.ifPresent(update);
        return value.isPresent();
    }

    public static <T> boolean updateValue(ComboBox<T> comboBox, Consumer<String> update) {
        T input = comboBox.getValue();
        Optional<String> value = Optional.ofNullable(input)
                                         .map(Object::toString)
                                         .filter(t -> !t.isEmpty());
        value.ifPresent(update);
        return value.isPresent();
    }

    public static boolean updateValue(DatePicker datePicker, Consumer<String> update) {
        LocalDate input = datePicker.getValue();
        Optional<String> value = Optional.ofNullable(input)
                                         .map(LocalDate::toString)
                                         .filter(t -> !t.isEmpty());
        value.ifPresent(update);
        return value.isPresent();
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());

            byte[] bytes = messageDigest.digest();
            StringBuilder hashedPassword = new StringBuilder();

            for (byte b : bytes) {
                hashedPassword.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
