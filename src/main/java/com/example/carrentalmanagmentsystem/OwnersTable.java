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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class OwnersTable extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private MenuButton ownerTypeMenuButton;

    @FXML
    private Button saveButton;

    @FXML
    private MenuItem bank;

    @FXML
    private MenuItem individual;




    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Owners.fxml"));
        Object root = loader.load();

        OwnersTable controller=loader.getController();

        controller.bank.setOnAction(e -> controller.setOwnerType("Bank"));
        controller.individual.setOnAction(e -> controller.setType("Individual"));
        controller.saveButton.setOnAction(e->controller.save());

        stage.setTitle("Owner Data");
        stage.setScene(new Scene((Parent) root, 1024, 768));
        stage.show();

    }



    private void save() {


        if(validateFields()) {
            String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";


            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO owners (FirstName, LastName,OwnerType) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, firstNameTextField.getText());
                    statement.setString(2, lastNameTextField.getText());
                    statement.setString(3, ownerTypeMenuButton.getText());


                    statement.executeUpdate();
                    System.out.println("Data added Successfully");
                    showErrorDialog("Data Added Successfully","Success");
                    clearFields();


                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorDialog("Error : "+e.getMessage(),"Error");

            }
        }
        else{
            showErrorDialog("Please fill in all fields.","Error");
        }
    }

    private void setType(String individual) {
        ownerTypeMenuButton.setText(individual);
    }

    private void setOwnerType(String bank) {
        ownerTypeMenuButton.setText(bank);
    }
    private void clearFields() {
        // Clear all input fields
        firstNameTextField.clear();
        lastNameTextField.clear();
    }
    private boolean validateFields() {
        // Check if all fields are filled
        return !firstNameTextField.getText().isEmpty() &&
                !lastNameTextField.getText().isEmpty() &&
                !ownerTypeMenuButton.getText().isEmpty();

    }

    private void showErrorDialog(String message,String title) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    }

