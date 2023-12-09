package com.example.workslink;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
//    space_details.fxml

    @Override
    public void start(Stage stage) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXML/loginRegistration.fxml"));
       //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXML/homePage-view.fxml"));
      // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXML/add-task.fxml"));
       //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXML/space_details.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}