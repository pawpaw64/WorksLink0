package com.example.workslink;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NoteController implements Initializable {




    @FXML
    private TextField noteNameTextField;

    @FXML
    private TextField yourNoteTextField;

    @FXML
    private VBox notesVBox = new VBox();

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        saveNotes();
//        OverViewDispaly();

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
    public void saveNotes() {
        // Clear all VBoxes
        notesVBox.getChildren().clear();
        //doingVbox.getChildren().clear();
        //completeVbox.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXML/note.fxml"));

            // Load separate instances of AnchorPane for each VBox
            AnchorPane NotePane = loader.load();
//            AnchorPane doingPane = loader.load();
//            AnchorPane completePane = loader.load();

            // Add each AnchorPane to its respective VBox
            notesVBox.getChildren().add(NotePane);
            //doingVbox.getChildren().add(doingPane);
            //  completeVbox.getChildren().add(completePane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
