package org.example.hotel.utils;

import org.example.hotel.enums.PaymentMethod;
import org.example.hotel.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
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

    public static List<String> getUserRoles() {
        List<String> roles = new ArrayList<>();

        for (Role role : Role.values()) {
            roles.add(StringUtil.capitalize(role.name().toLowerCase()));
        }

        return roles;
    }

    public static List<String> getPaymentMethods() {
        List<String> paymentMethods = new ArrayList<>();

        for (PaymentMethod method : PaymentMethod.values()) {
            paymentMethods.add(StringUtil.capitalize(method.name().toLowerCase()));
        }

        return paymentMethods;
    }
}
