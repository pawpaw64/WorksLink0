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
        String noteName = noteNameTextField.getText();
        String noteText = yourNoteTextField.getText();

        // Load the new note pane FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/note.fxml"));
        Parent newNotePaneRoot = loader.load();

        // Access the controller of the new note pane
        addPaneController addPaneController = loader.getController();

        // Set the note information in the new note pane
        addPaneController.setNoteInfo(noteName, noteText);

        // Access the controller of the main FXML
        NoteController noteController = new NoteController();

        // Call the method in the main FXML to add the new note pane to the VBox
        noteController.addNoteToVBox(newNotePaneRoot);

        // Close the current stage (assuming FXML 2 is opened in a separate stage)
        Stage stage = (Stage) noteNameTextField.getScene().getWindow();
        stage.close();
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
