package com.example.workslink;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SaveNotesController {
    @FXML
    private Label noteName;

    @FXML
    private Label noteitem;
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

}
