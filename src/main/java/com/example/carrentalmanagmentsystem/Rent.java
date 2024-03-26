package com.example.carrentalmanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Rent extends Application {

    @FXML
    private TextField carRegNoTextField;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;
    @FXML
    private TextField noOfDays;

    @FXML
    private TextField KmDriven;

    @FXML
    private MenuButton bookingType;

    @FXML
    private MenuItem pickDrop;

    @FXML
    private MenuItem days;
    @FXML
    private Button saveButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Rent.fxml"));
        Parent root = loader.load();

        Rent controller = loader.getController();
        controller.pickDrop.setOnAction(e -> controller.setBookingType("Pick/Drop"));
        controller.days.setOnAction(e -> controller.setBookingType("Day/Days"));
        controller.saveButton.setOnAction(e -> controller.save());

        stage.setTitle("Rent Details");
        stage.setScene(new Scene(root, 1024, 768));
        stage.show();
    }
    public void setBookingType(String type) {
        bookingType.setText(type);
    }

    private void save() {
        if (validateFields()) {
            String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO RENT (CustomerID, RegID, BookingType,NoOfDays,KMsDriven,StartDate, EndDate) VALUES (?, ?, ?, ?, ? ,?,?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, customerIdTextField.getText());
                    statement.setString(2, carRegNoTextField.getText());
                    statement.setString(3, bookingType.getText());
                    statement.setString(4, noOfDays.getText());
                    statement.setString(5, KmDriven.getText());
                    statement.setObject(6, startDate.getValue());
                    statement.setObject(7, endDate.getValue());

                    statement.executeUpdate();
                    System.out.println("Data added Successfully");
                    showErrorDialog("Data Added Successfully");
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorDialog("Error: " + e.getMessage());
            }
        } else {
            showErrorDialog("Please fill in all fields.");
        }
    }

    private void clearFields() {
        carRegNoTextField.clear();
        customerIdTextField.clear();
        noOfDays.clear();
        KmDriven.clear();
        bookingType.setText("Booking Type");
        startDate.setValue(null);
        endDate.setValue(null);
    }

    private boolean validateFields() {
        return !carRegNoTextField.getText().isEmpty() &&
                !customerIdTextField.getText().isEmpty() &&
                startDate.getValue() != null &&
                endDate.getValue() != null;

    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
