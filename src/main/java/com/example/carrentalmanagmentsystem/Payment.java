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

public class Payment extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @FXML
    private MenuButton DuesCleared;

    @FXML
    private MenuItem yes;

    @FXML
    private MenuItem no;

    @FXML
    private TextField payId;
    @FXML
    private TextField carRegNoTextField;

    @FXML
    private TextField rentIdTextField;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private Button saveButton;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        Parent root = loader.load();

        Payment controller = loader.getController();
        controller.yes.setOnAction(e -> controller.setType("Yes"));
        controller.no.setOnAction(e -> controller.setType("No"));
        controller.saveButton.setOnAction(e -> controller.save());

        stage.setTitle("Payment Details");
        stage.setScene(new Scene(root, 1024, 768));
        stage.show();
    }

    private void setType(String s) {
        DuesCleared.setText(s);
    }

    private void save() {
        if (validateFields()) {

            String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";


            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO PAYMENT (PayID,DuesCleared,CustomerID, RegID,RentID) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, payId.getText());
                    statement.setString(2, DuesCleared.getText());
                    statement.setString(3, customerIdTextField.getText());
                    statement.setString(4, carRegNoTextField.getText());
                    statement.setInt(5, Integer.parseInt(rentIdTextField.getText()));



                    statement.executeUpdate();
                    showErrorDialog("Data Added Successfully");
                    System.out.println("Data added Successfully");
                    clearFields();


                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorDialog("Error : "+e.getMessage());

            }

        } else {
            showErrorDialog("Please fill in all fields.");
        }
    }

    private void clearFields() {
        // Clear all input fields
        carRegNoTextField.clear();
        rentIdTextField.clear();
        customerIdTextField.clear();
        payId.clear();
        DuesCleared.setText("Dues Cleared");
    }

    private boolean validateFields() {
        // Check if all fields are filled
        return !carRegNoTextField.getText().isEmpty() &&
                !rentIdTextField.getText().isEmpty() &&
                !customerIdTextField.getText().isEmpty() &&
                !payId.getText().isEmpty() &&
                !DuesCleared.getText().equals("Dues Cleared");


    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
