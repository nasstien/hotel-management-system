package org.example.hotel.utils;

import org.example.hotel.dao.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseUtil {
    public static void setParams(PreparedStatement statement, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
    }

    public static <T> boolean updateValue(T input, Consumer<String> update) {
        Optional<String> value = Optional.ofNullable(input)
                                         .map(Object::toString)
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
}
