package com.example.workslink;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class NoteController {

    @FXML
    private AnchorPane addingPane;

    @FXML
    private Label noteName;

    @FXML
    private Label noteitem;

    @FXML
    private TextField noteNameTextField;

    @FXML
    private TextField yourNoteTextField;

    @FXML
    private VBox notesVBox;

    private Stage stage;

    @FXML
    private void addNote() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/CreateNote.fxml"));
        Parent root = loader.load();

        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(root));

        // Set this controller as the controller for the Scene 2
        NoteController noteController = loader.getController();
        noteController.setStage(secondStage);

        secondStage.show();
    }

    @FXML
    private void saveNote() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/note.fxml"));
        Parent root = loader.load();

        NoteController noteController = loader.getController();
        noteController.setNoteDetails(noteNameTextField.getText(), yourNoteTextField.getText());

        // Add the note display to the dashboard
        notesVBox.getChildren().add(root);

        // Close the current stage (Scene 2)
        stage.close();
    }
    @FXML
    private Button closeButton;
    @FXML
    public void closeOnAction() {
        System.out.println("CLose");
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNoteDetails(String title, String content) {
        noteName.setText(title);
        noteitem.setText(content);
    }
}
