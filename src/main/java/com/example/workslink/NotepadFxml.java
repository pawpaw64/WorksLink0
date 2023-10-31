package com.example.workslink;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class NotepadFxml extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("notepad.fxml"));
        primaryStage.setTitle("FXML Notepad");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        Button addButton = (Button) root.lookup("#addButton");
        TextArea textArea = new TextArea();
        addButton.setOnAction(event -> {
            textArea.clear();
            Stage noteStage = new Stage();
            noteStage.setTitle("New Note");
            noteStage.setScene(new Scene(textArea, 300, 200));
            noteStage.show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
