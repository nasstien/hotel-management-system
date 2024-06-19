package org.example.hotel.controllers;

import org.example.hotel.dao.UserDAO;
import org.example.hotel.models.User;
import org.example.hotel.utils.Util;
import org.example.hotel.utils.Validator;
import org.example.hotel.utils.gui.Message;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.util.Date;

import static org.example.hotel.Application.*;

public class SettingsController {
    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField emailField;
    @FXML TextField phoneNumField;
    @FXML TextField passportNumField;
    @FXML TextField positionField;
    @FXML TextField salaryField;
    @FXML PasswordField passwordField;
    @FXML PasswordField passwordConfirmField;

    @FXML
    public void initialize() {
        firstNameField.setPromptText(user.getFirstName());
        lastNameField.setPromptText(user.getLastName());
        emailField.setPromptText(user.getEmail());
        phoneNumField.setPromptText(user.getPhoneNum());
        passportNumField.setPromptText(user.getPassportNum());
        positionField.setPromptText(user.getPosition());
        salaryField.setPromptText(STR."\{user.getSalary()}");
    }

    @FXML
    private void handleSaveClick() {
        String errMessage = "";

        if (!emailField.getText().isEmpty() && !Validator.isValidEmail(emailField.getText())) {
            errMessage = "Email is invalid.";
        } else if (!phoneNumField.getText().isEmpty() && !Validator.isValidPhoneNum(phoneNumField.getText())) {
            errMessage = "Phone number must start with \"+\" and contain only integers.";
        } else if (!salaryField.getText().isEmpty() && !Validator.isDouble(salaryField.getText())) {
            errMessage = "Salary must be a double.";
        } else if (!passwordField.getText().isEmpty() && !Validator.isValidPassword(passwordField.getText())) {
            errMessage = "Password must be at least 8 characters long and include a combination of letters, numbers, and special characters.";
        } else if (!passportNumField.getText().isEmpty() && passwordConfirmField.getText().isEmpty()) {
            errMessage = "Please confirm password.";
        } else if (!passwordField.getText().isEmpty() &&
                !passwordConfirmField.getText().isEmpty() &&
                !passwordField.getText().equals(passwordConfirmField.getText())) {
            errMessage = "Password and password confirmation fields are different.";
        }

        if (!errMessage.isEmpty()) {
            Message.displayErrorDialog(errMessage);
            return;
        }

        boolean modified = false;
        User updatedUser = new User(user);

        modified |= Util.updateValue(firstNameField, updatedUser::setFirstName);
        modified |= Util.updateValue(lastNameField, updatedUser::setLastName);
        modified |= Util.updateValue(emailField, updatedUser::setEmail);
        modified |= Util.updateValue(phoneNumField, updatedUser::setPhoneNum);
        modified |= Util.updateValue(passportNumField, updatedUser::setPassportNum);
        modified |= Util.updateValue(positionField, updatedUser::setPosition);
        modified |= Util.updateValue(salaryField, salary -> updatedUser.setSalary(Double.parseDouble(salary)));
        modified |= Util.updateValue(passwordField, updatedUser::setPassword);

        if (modified) {
            updatedUser.setUpdatedAt(new Date());

            int userId = new UserDAO().getId(user);
            new UserDAO().update(userId, updatedUser);

            Message.displaySuccessDialog("Account info has been successfully saved.\nPlease restart this application for the changes to take effect.");
        } else {
            Message.displaySuccessDialog("Account info has not been modified.");
        }

        history.pop();
    }

    @FXML
    private void handleDeleteClick() {
        Message.displayConfirmDialog("Are you sure you want to delete an account?", this::deleteAccount);
    }

    private void deleteAccount() {
        int userId = new UserDAO().getId(user);
        new UserDAO().delete(userId);
        Message.displaySuccessDialog("Account has been successfully deleted.");

        try {
            primaryStage.setScene(login());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        user = null;
        history.clear();
    }

    @FXML
    private void handleBackClick() {
        history.pop();
    }
 }
