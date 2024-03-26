package com.example.carrentalmanagmentsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class HomeScreen extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private TextField ownerID;
    @FXML
    private TextField carIdField;
    @FXML
    private TextField carNameField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField dailyRateField;

    @FXML
    private TextField color;

    @FXML
    private TextField companyName;

    @FXML
    private Button saveButton;
    @FXML
    private Button addCar;

    @FXML
    private Button addOwner;

    @FXML
    private Button addRent;
     @FXML
    private Button availableCars;


    @FXML
    private Button updateDailyRate;
    @FXML
    private Button addCustomer;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        Object root = loader.load();

        // Set the controller
        HomeScreen controller = loader.getController();
        controller.addCar.setOnAction(e -> controller.addCarTable());
        controller.addOwner.setOnAction(e->controller.ownerTable());
        controller.availableCars.setOnAction(e->{
            Platform.runLater(() -> {
                try {
                    Availability available = new Availability();
                    Stage homeStage = new Stage();
                    available.start(homeStage);

                    // TODO: Add actions to perform after successful login
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        });
        controller.addRent.setOnAction(e->{
            Platform.runLater(() -> {
                try {
                    Rent rent = new Rent();
                    Stage homeStage = new Stage();
                    rent.start(homeStage);

                    // TODO: Add actions to perform after successful login
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        });

        controller.addCustomer.setOnAction(e->{
            Platform.runLater(() -> {
                try {
                    CustomerDetails customer = new CustomerDetails();
                    Stage homeStage = new Stage();
                    customer.start(homeStage);

                    // TODO: Add actions to perform after successful login
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        });
        controller.updateDailyRate.setOnAction(e->{
            Platform.runLater(() -> {
                try {
                    updateCarTable update = new updateCarTable();
                    Stage homeStage = new Stage();
                    update.start(homeStage);

                    // TODO: Add actions to perform after successful login
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        });


        // Set any additional configurations for the controller if needed


        stage.setTitle("Home Screen");
        stage.setScene(new Scene((Parent) root, 1024, 600));
        stage.show();

    }

    private void ownerTable(){

        Platform.runLater(() -> {
            try {
                OwnersTable owner=new OwnersTable();
                Stage homeStage = new Stage();
                owner.start(homeStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void saveData() {
        if (validateFields()) {
            // Fields are valid, proceed to save data
            String url = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

            try (Connection connection = DriverManager.getConnection(url)) {
                String sql = "INSERT INTO Car (RegID, CarName,CompanyName, Model,DailyRate,Color,OwnerID) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, carIdField.getText());
                    statement.setString(2, carNameField.getText());
                    statement.setString(3, companyName.getText());
                    statement.setString(4, modelField.getText());
                    statement.setDouble(5, Double.parseDouble(dailyRateField.getText()));
                    statement.setString(6, color.getText());
                    statement.setInt(7, Integer.parseInt(ownerID.getText()));


                    statement.executeUpdate();

                    // Display a success message or clear the fields.
                    System.out.println("Data saved successfully!");
                    showErrorDialog("Data Added Successfully","Success");

                    // Clear all fields for new data entry
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error Occurred");
                // Handle the SQL exception (e.g., display an error message).
            }

        } else {
            // Fields are not valid, display an error message or take appropriate action.
            showErrorDialog("Please fill in all fields.","Error");
        }
    }

    private boolean validateFields() {
        // Check if all fields are filled
        return !carIdField.getText().isEmpty() &&
                !carNameField.getText().isEmpty() &&
                !modelField.getText().isEmpty() &&
                !dailyRateField.getText().isEmpty()&&
                !color.getText().isEmpty()&&
                !companyName.getText().isEmpty() &&
                ! ownerID.getText().isEmpty();

    }

    private void clearFields() {
        // Clear all input fields
        carIdField.clear();
        carNameField.clear();
        modelField.clear();
        dailyRateField.clear();
        color.clear();
        companyName.clear();
        ownerID.clear();

    }

    private void addCarTable(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();


            Stage carTable = new Stage();
            carTable.setTitle("Car Rental Management System");
            carTable.setScene(new Scene(root, 1024, 700));


            HomeScreen controller = loader.getController();
            controller.saveButton.setOnAction(e -> controller.saveData());
            carTable.show();

            // Close the HomeScreen stage (optional)
            ((Stage) addCar.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showErrorDialog(String message,String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
