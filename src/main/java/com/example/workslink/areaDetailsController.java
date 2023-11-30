package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class areaDetailsController {
    @FXML
    TabPane tabPane;
    @FXML
    StackPane stack1 = new StackPane();
    @FXML
    Tab task;

    @FXML
    void add_task(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/workslink/FXML/add-task.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));

            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    Button TaskButton;
    @FXML
    Button AssigneMember;
    @FXML
    Button Due_Date;

    public void task_operation(){
        List<String > items = List.of("ToDo","Doing","Complete");
        VBox vbox = createListViewVBox(items,116,210);
        PopOver popOver = new PopOver(vbox);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        vbox.getStylesheets().add(getClass().getResource("CSS/popOver.css").toExternalForm());

        double screenX = TaskButton.localToScreen(TaskButton.getBoundsInLocal()).getMinX();
        double screenY = TaskButton.localToScreen(TaskButton.getBoundsInLocal()).getMaxY();

        double adjustedX = screenX - (vbox.getWidth() - TaskButton.getBoundsInParent().getWidth()) / 2;
        double adjustedY = screenY;

        popOver.show(TaskButton, adjustedX, adjustedY);
    }
    public void assign_Member(){
        VBox vbox1 = new VBox();
        PopOver popOver = new PopOver(vbox1);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);

        double screenX = AssigneMember.localToScreen(AssigneMember.getBoundsInLocal()).getMinX();
        double screenY = AssigneMember.localToScreen(AssigneMember.getBoundsInLocal()).getMaxY();

        double adjustedX = screenX - (vbox1.getWidth() - AssigneMember.getBoundsInParent().getWidth()) / 2;
        double adjustedY = screenY;


        popOver.show(AssigneMember);

    }
    public void pickDate(){
        DatePicker datePicker = new DatePicker();

        PopOver popOver = new PopOver(datePicker);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
//        double screenX = Due_Date.localToScreen(Due_Date.getBoundsInLocal()).getMinX();
//        double screenY = Due_Date.localToScreen(Due_Date.getBoundsInLocal()).getMaxY();
//
//        double adjustedX = screenX - (vbox2.getWidth() - Due_Date.getBoundsInParent().getWidth()) / 2;
//        double adjustedY = screenY;


        popOver.show(Due_Date);
        datePicker.setOnAction(actionEvent -> {
//            Due_Date.setText(datePicker.getValue().toString());
            LocalDate myDate = datePicker.getValue();
            String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yy"));
            Due_Date.setText(myFormattedDate);
        });

    }
    private VBox createListViewVBox(List<String> items ,int height, int width) {
        VBox vbox = new VBox();
        vbox.setPrefHeight(height);
        vbox.setPrefWidth(width);
        vbox.getStylesheets().add(getClass().getResource("CSS/popOver.css").toExternalForm());
        ListView<String> listView = new ListView<>();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        listView.setItems(observableItems);
        listView.setFixedCellSize(-3);
        // Add a listener to the selection model to handle item clicks
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleItemClick(newValue); // Call a method to handle the selected item
            }
        });
        vbox.getChildren().add(listView);
        return vbox;
    }
    private void handleItemClick(String selectedItem) {
        // Add logic to perform actions based on the selected item
//        switch (selectedItem) {
//            case "Calculator":
//                openCalculator();
//                break;
//            case "Notes":
//                openNotes();
//                break;
//            case "More":
//                // Handle more options
//                break;
//            // Add more cases as needed
//        }
    }
}

