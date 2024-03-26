// SignUpController.java
package com.example.carrentalmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signupButton;


    @FXML
    public void saveUser() {
        // The rest of your saveUser method remains unchanged...
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String dob = dobPicker.getValue().toString();  // Adjust as needed
        String username = usernameField.getText();
        String password = passwordField.getText();

        String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // Query to insert the new user data into the database
            String query = "INSERT INTO Users (FirstName, LastName, Email, Phone, DOB, Username, Password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, dob);
                preparedStatement.setString(6, username);
                preparedStatement.setString(7, password);

                preparedStatement.executeUpdate();

                // TODO: Add actions to perform after successful signup

                System.out.println("Signup successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }

    }

    public void showSignUpScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = loader.load();

            // Create a new stage for the SignUp screen
            Stage signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.setScene(new Scene(root, 753, 442
            ));

            // Show the SignUp screen
            SignUpController controller = loader.getController();
            controller.signupButton.setOnAction(e->controller.saveUser());
            signUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The rest of your SignUpController class remains unchanged...
}
