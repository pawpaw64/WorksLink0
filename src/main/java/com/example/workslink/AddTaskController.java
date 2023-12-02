//add_task.fxml controller
package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AddTaskController implements Initializable {
    @FXML
    private Button AssigneMember;

    @FXML
    private Button CreateTask;

    @FXML
    private Button Due_Date;

    @FXML
    private Button TaskButton;
    String selectedTask;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void task_operation(){ //todo button work

        List<String >items = List.of("ToDo","Doing","Complete");
        VBox vbox = createListViewVBox(items,116,210);
        PopOver popOver = new PopOver(vbox);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        vbox.getStylesheets().add(getClass().getResource("CSS/popOver.css").toExternalForm());

        double screenX = TaskButton.localToScreen(TaskButton.getBoundsInLocal()).getMinX();
        double screenY = TaskButton.localToScreen(TaskButton.getBoundsInLocal()).getMaxY();

        double adjustedX = screenX - (vbox.getWidth() - TaskButton.getBoundsInParent().getWidth()) / 2;
        double adjustedY = screenY;

        popOver.show(TaskButton, adjustedX, adjustedY);

        ListView<String> listView = (ListView<String>) vbox.getChildren().get(0);
        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            // Update the label of the button based on the selected item
            TaskButton.setText(selectedItem);
            popOver.hide();
        });

    }
    public void assign_Member(){
        List<String >items = List.of("Hasib","Rana","Sumaiya","Prince");
        VBox vbox1 = createListViewVBox(items,116,210);
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
    @FXML
    public void createTask(){
        System.out.println("H");
        selectedTask = TaskButton.getText();


        // Depending on the selected task, create a corresponding pane or perform other actions
        switch (selectedTask) {
            case "ToDo":
                createToDoPane();
                break;
            case "Doing":
                createDoingPane();
                break;
            case "Complete":
                createCompletePane();
                break;
            default:
                // Handle other cases or provide a default behavior
                break;
        }
    }

    private void createCompletePane() {

    }

    private void createDoingPane() {

    }

    private void createToDoPane() {
        System.out.println("H4");
        Pane toDoPane = new Pane();
        Label label = new Label("ToDo Task");
        toDoPane.getChildren().add(label);

        // Assuming vbox is the appropriate VBox you want to add the pane to
        areaDetailsController controller = getAreaDetailsController();
        if (controller != null) {
            controller.addPaneToVBox(toDoPane, controller.getTodoVbox());
        }

    }
    private areaDetailsController getAreaDetailsController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/workslink/FXML/area_details.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader.getController();
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
