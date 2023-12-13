package com.example.workslink;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;


public class NotificationView extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ControlsFX Notifications Example");

        Button showNotificationButton = new Button("Show Notification");
        showNotificationButton.setOnAction(e -> showNotification());

        StackPane root = new StackPane();
        root.getChildren().add(showNotificationButton);
        root.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();



    }

    private void showNotification() {
        Notifications.create()
                .title("Notification Title")
                .text("This is a sample notification.")
                .showInformation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
