package com.example.workslink;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveNotesController implements Initializable {
    @FXML
    private Label noteName = new Label();

    @FXML
    private Label noteitem = new Label();
    public Label getNoteName() {
        return noteName;
    }

    public void setNoteName(Label noteName) {
        this.noteName = noteName;
    }

    public Label getNoteitem() {
        return noteitem;
    }

    public void setNoteitem(Label noteitem) {
        this.noteitem = noteitem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
