package com.example.carrentalmanagmentsystem;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;



    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Object root = loader.load();

        // Set the controller
        LoginScreen controller = loader.getController();
        controller.loginButton.setOnAction(e -> controller.handleLoginButton());
        // Set any additional configurations for the controller if needed


        stage.setTitle("Login Screen");
        stage.setScene(new Scene((Parent) root, 764, 450));
        stage.show();

    }
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // Query to check if the username and password exist in the database
            String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Close the current stage
                        Stage currentStage = (Stage) loginButton.getScene().getWindow();
                        currentStage.close();

                        // Launch the HomeScreen application
                        Platform.runLater(() -> {
                            try {
                                HomeScreen homeScreen = new HomeScreen();
                                Stage homeStage = new Stage();
                                homeScreen.start(homeStage);

                                System.out.println("Login successful!");
                                // TODO: Add actions to perform after successful login
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        // User does not exist or incorrect credentials
                        System.out.println("Invalid username or password");
                        // TODO: Display an error message or take appropriate action
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }


}


