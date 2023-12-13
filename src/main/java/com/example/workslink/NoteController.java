package com.example.workslink;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NoteController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private VBox notesVBox = new VBox();

    @FXML
    private void addNote() throws IOException {
        // Load the new note FXML
        // Add your code to load the new note FXML here
        // For simplicity, let's assume the FXML file is named "CreateNote.fxml"
         FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/CreateNote.fxml"));
         Parent newNoteRoot = loader.load();
         Stage newNoteStage = new Stage();
         newNoteStage.setScene(new Scene(newNoteRoot));
         newNoteStage.show();
    }


    @FXML
    private TextField noteNameTextField = new TextField();

    @FXML
    private TextField yourNoteTextField = new TextField();

//    private VBox notesVBox; // Add this variable to store the reference to the VBox

    // Inject the VBox from the first FXML
    public void setNotesVBox(boolean add) {
        this.notesVBox = notesVBox;
    }
    @FXML
    private Button saveButton;

    @FXML
    private void saveNote() throws IOException {

    }

    private void addNoteToVBox(Parent newNotePaneRoot) {
    }


    // Add a method to get the VBox from the first FXML
    @FXML
    Button closeButton = new Button();
    @FXML
    private void closeScene() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
