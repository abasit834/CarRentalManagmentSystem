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

public class updateCarTable extends Application {

    @FXML
    private TextField carRegNoTextField;

    @FXML
    private MenuButton selectColumnMenuButton;

    @FXML
    private MenuItem dailyRate;

    @FXML
    private TextField updatedValueTextField;

    @FXML
    private Button update;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCar.fxml"));
        Object root = loader.load();

        updateCarTable controller= loader.getController();
        controller.dailyRate.setOnAction(e -> controller.setDailyRate("DailyRate"));
        controller.update.setOnAction(e->controller.handleUpdateButton());


        primaryStage.setTitle("Car Update Application");
        primaryStage.setScene(new Scene((Parent) root, 1024, 768));
        primaryStage.show();


    }

    @FXML
    private void handleUpdateButton() {
        String carRegNo = carRegNoTextField.getText();
        String selectedColumn = selectColumnMenuButton.getText();
        String updatedValue = updatedValueTextField.getText();

        String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
        String updateQuery = "UPDATE Car SET " + selectedColumn + " = ? WHERE RegID = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedValue);
            preparedStatement.setString(2, carRegNo);
            preparedStatement.executeUpdate();

            showErrorDialog("Record updated successfully.");

            System.out.println("Record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (log, display error message, etc.)
        }
    }
    public void setDailyRate(String type) {
        selectColumnMenuButton.setText(type);
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }
