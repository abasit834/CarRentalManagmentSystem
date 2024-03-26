module com.example.carrentalmanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires mssql.jdbc;


    opens com.example.carrentalmanagmentsystem to javafx.fxml;
    exports com.example.carrentalmanagmentsystem;
}