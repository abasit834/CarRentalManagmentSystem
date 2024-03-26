package com.example.carrentalmanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartScreen extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private Button SignUp;
    @FXML
    private Button Login;

    SignUpController obj=new SignUpController();
    LoginScreen obj2=new LoginScreen();
    Stage Loginstage=new Stage();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
        Object root = loader.load();

        // Set the controller
        StartScreen controller = loader.getController();
        controller.SignUp.setOnAction(e -> controller.obj.showSignUpScreen());
        controller.Login.setOnAction(e -> {
            try {
                controller.obj2.start(Loginstage);
                stage.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        // Set any additional configurations for the controller if needed


        stage.setTitle("Home Screen");
        stage.setScene(new Scene((Parent) root, 600, 375));
        stage.show();

    }
}
