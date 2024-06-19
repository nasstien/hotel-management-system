package org.example.hotel.utils;

import javafx.scene.control.TextField;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String input) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean isValidPhoneNum(String input) {
        String regex = "^\\+\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean isValidPassword(String input) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean isValidTime(String input) {
        String regex = "^([0-9]|[01][0-9]|2[0-3]):([0-5][0-9])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean isValidDateInterval(Date startDate, Date endDate) {
        if(startDate == null || endDate == null) return false;
        return startDate.before(endDate);
    }

    public static boolean isEmptyFields(TextField[] fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
