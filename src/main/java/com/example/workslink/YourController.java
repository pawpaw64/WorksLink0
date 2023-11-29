package com.example.workslink;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class YourController {
    @FXML
    private ScrollPane taskScrollPane;

    @FXML
    private AnchorPane taskAnchorPane;

    @FXML
    private void addTaskToScrollPane() {
        // Create a new VBox
        VBox newVBox = new VBox();
        newVBox.setPrefSize(170.0, 47.0);
        newVBox.setStyle("-fx-border-color: blue;");

        //add new v box
        taskAnchorPane.getChildren().add(newVBox);

        // Optionally, adjust the ScrollPane to scroll to the bottom
        taskScrollPane.setVvalue(1.0);
    }
}
