package com.example.workslink;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Notepad extends Application {

    private List<String> notes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Notepad");

        // Create the main layout
        BorderPane mainLayout = new BorderPane();
        //mainLayout.setStyle("-fx-background-color: #f0f0f0;");
        mainLayout.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox topMenu = new HBox();
        Button addButton = new Button("+");

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        topMenu.getChildren().add(addButton);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.setPadding(new Insets(10));
        BorderPane.setAlignment(addButton, Pos.TOP_LEFT);
        mainLayout.setTop(topMenu);

        VBox notesContainer = new VBox();
        notesContainer.setAlignment(Pos.TOP_CENTER);
        notesContainer.setSpacing(10);
        mainLayout.setCenter(notesContainer);

        addButton.setOnAction(e -> {
            addNewNote();
            refreshNotesContainer(notesContainer);
        });

        // Show the stage
        Scene scene = new Scene(mainLayout, 400, 400);
        scene.getStylesheets().add("styles.css"); // External CSS file for additional styling
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addNewNote() {
        Stage noteStage = new Stage();
        VBox noteLayout = new VBox();
        TextArea noteTextArea = new TextArea();
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        noteLayout.getChildren().addAll(noteTextArea, saveButton);

        saveButton.setOnAction(e -> {
            notes.add(noteTextArea.getText());
            noteStage.close();
        });

        Scene scene = new Scene(noteLayout, 300, 200);
        scene.getStylesheets().add("styles.css"); // External CSS file for additional styling
        noteStage.setScene(scene);
        noteStage.show();
    }

    private void refreshNotesContainer(VBox notesContainer) {
        notesContainer.getChildren().clear();
        for (String note : notes) {
            TextArea noteTextArea = new TextArea(note);
            noteTextArea.setPrefRowCount(2);
            noteTextArea.setPrefColumnCount(20);
            notesContainer.getChildren().add(noteTextArea);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
