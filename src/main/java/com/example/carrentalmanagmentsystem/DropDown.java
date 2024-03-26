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

public class DropDown extends Application {



    @FXML
    private TextField roomNoField;

    @FXML
    private MenuButton roomTypeField;

    @FXML
    private MenuItem suiteItem;

    @FXML
    private MenuItem normalRoomItem;
    @FXML
    private TextField priceField;

    @FXML
    private MenuButton availabilityField;

    @FXML
    private MenuItem availableItem;

    @FXML
    private MenuItem occupiedItem;

    @FXML
    private Button insertButton;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dropdown.fxml"));
        Parent root = loader.load();

        // Assuming roomTypeField is declared as ComboBox<StringProperty>



        DropDown controller = loader.getController();
        controller.suiteItem.setOnAction(e -> controller.setRoomType("Suite"));
        controller.normalRoomItem.setOnAction(e -> controller.setRoomType("Normal Room"));
        controller.availableItem.setOnAction(e -> controller.setAvailability("Available"));
        controller.occupiedItem.setOnAction(e -> controller.setAvailability("Occupied"));
        controller.insertButton.setOnAction(e->controller.insertRoom());



        primaryStage.setTitle("Room Insertion");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public void insertRoom() {
        String roomNo = roomNoField.getText();
        String roomType = roomTypeField.getText();  // Retrieve selected value
        String price = priceField.getText();
        String availability = availabilityField.getText();  // Retrieve selected value

        String DB_URL = "jdbc:sqlserver://DESKTOP-EBBCS4S\\SQLEXPRESS01:64677;databaseName=HotelManagementSystem;encrypt=false";
        String USERNAME = "root";
        String PASSWORD = "root";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String query = "INSERT INTO Room (RoomNo, RoomType, Price, Availability) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, roomNo);
                preparedStatement.setString(2, roomType);
                preparedStatement.setString(3, price);
                preparedStatement.setString(4, availability);

                preparedStatement.executeUpdate();

                System.out.println("Room added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRoomType(String type) {
        roomTypeField.setText(type);
    }
    public void setAvailability(String status) {
        availabilityField.setText(status);
    }
}