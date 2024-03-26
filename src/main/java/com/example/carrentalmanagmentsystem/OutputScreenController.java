package com.example.carrentalmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OutputScreenController implements Initializable {

    @FXML
    private TableView<Car> outputTable;

    @FXML
    private TableColumn<Car, String> regIdColumn;

    @FXML
    private TableColumn<Car, String> carNameColumn;

    @FXML
    private TableColumn<Car, String> companyNameColumn;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, Double> dailyRateColumn;

    @FXML
    private TableColumn<Car, String> colorColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize columns with appropriate property values
        regIdColumn.setCellValueFactory(new PropertyValueFactory<>("regId"));
        carNameColumn.setCellValueFactory(new PropertyValueFactory<>("carName"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        dailyRateColumn.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        // Load data from the database and populate the table
        loadData();
    }

    private void loadData() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT C.* FROM CAR AS C WHERE C.RegID NOT IN (SELECT R.RegID FROM RENT AS R WHERE R.StartDate <= CONVERT(DATE, GETDATE()) AND R.EndDate >= CONVERT(DATE, GETDATE()))")) {

            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getString("RegID"),
                        resultSet.getString("CarName"),
                        resultSet.getString("CompanyName"),
                        resultSet.getString("Model"),
                        resultSet.getDouble("DailyRate"),
                        resultSet.getString("Color")
                );
                outputTable.getItems().add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

