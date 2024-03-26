package com.example.carrentalmanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Desktop;

public class CustomerDetails extends Application {

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private MenuButton genderMenuButton;

    @FXML
    private MenuItem action1MenuItem;

    @FXML
    private MenuItem action2MenuItem;

    @FXML
    private TextField houseNoTextField;

    @FXML
    private TextField streetNoTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private Button save;

    @FXML
    private TextField sector;

    @FXML
    private TextField area;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customers.fxml"));
        Parent root = loader.load();

        CustomerDetails controller = loader.getController();
        controller.action1MenuItem.setOnAction(e -> controller.setGender("Male"));
        controller.action2MenuItem.setOnAction(e -> controller.setGender("Female"));
        controller.save.setOnAction(e->controller.save());

        stage.setTitle("Customer Details");
        stage.setScene(new Scene(root, 1024, 768));
        stage.show();
    }

    private void setGender(String gender) {
        genderMenuButton.setText(gender);
    }

    private void save() {
        if (validateFields()) {
            String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CarRentalManagementSystem;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO CUSTOMER (CustomerID, CustomerFirstName, CustomerLastName, HouseNo, Street, Sector, Area, City, Gender, PhoneNo, CustomrCompanyName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, customerIdTextField.getText());
                    statement.setString(2, firstNameTextField.getText());
                    statement.setString(3, lastNameTextField.getText());
                    statement.setInt(4, Integer.parseInt(houseNoTextField.getText()));
                    statement.setInt(5, Integer.parseInt(streetNoTextField.getText()));
                    statement.setString(6, sector.getText());
                    statement.setString(7, area.getText());
                    statement.setString(8, cityTextField.getText());
                    statement.setString(9, genderMenuButton.getText());
                    statement.setString(10, phoneTextField.getText());
                    statement.setString(11, companyNameTextField.getText());




                    statement.executeUpdate();
                    System.out.println("Data added Successfully");
                    showErrorDialog("Data Added Successfully","Success");
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorDialog("Error: " + e.getMessage(),"Error");
            }
        } else {
            showErrorDialog("Please fill in all fields.","Error");
        }
    }

    private void clearFields() {
        customerIdTextField.clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneTextField.clear();
        companyNameTextField.clear();
        genderMenuButton.setText("Select Gender");
        houseNoTextField.clear();
        streetNoTextField.clear();
        cityTextField.clear();
    }

    private boolean validateFields() {
        return !customerIdTextField.getText().isEmpty() &&
                !firstNameTextField.getText().isEmpty() &&
                !lastNameTextField.getText().isEmpty() &&
                !phoneTextField.getText().isEmpty() &&
                !companyNameTextField.getText().isEmpty() &&
                !genderMenuButton.getText().equals("Select Gender") &&
                !houseNoTextField.getText().isEmpty() &&
                !streetNoTextField.getText().isEmpty() &&
                !cityTextField.getText().isEmpty();
    }

    private void showErrorDialog(String message,String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void generatePDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Hello, this is a sample PDF generated from JavaFX!");
            contentStream.endText();
            contentStream.close();

            File pdfFile = new File("output.pdf");
            document.save(pdfFile);
            document.close();

            // Open the PDF file with the default PDF viewer
            Desktop desktop = Desktop.getDesktop();
            desktop.open(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
