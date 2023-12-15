package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskAction implements Initializable {

    @FXML
    private Text taskDes;

    @FXML
    private Label taskName;

    @FXML
    private Button taskSave;

    @FXML
    private ComboBox<String> taskStatus;

    @FXML
    private ChoiceBox<String> taskPriority;
    private ObservableList<String> statusOptions = FXCollections.observableArrayList("To Do", "In Progress", "Done");
    private ObservableList<String> priorityOptions = FXCollections.observableArrayList("High", "Medium", "Low");
    @FXML
    void goback(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
    TaskHistoryData taskHistoryData;

    public void setTaskData(TaskHistoryData selectedTask) {
        this.taskHistoryData=selectedTask;
        // If the task priority is not in the options, add it
        if (!priorityOptions.contains(selectedTask.getTaskPriority())) {
            priorityOptions.add(selectedTask.getTaskPriority());
        }
        taskPriority.setValue(selectedTask.getTaskPriority());

        // If the task status is not in the options, add it
        if (!statusOptions.contains(selectedTask.getTaskStatus())) {
            statusOptions.add(selectedTask.getTaskStatus());
        }
        taskPriority.setItems(priorityOptions);
        taskStatus.setItems(statusOptions);



        // Access the selected task's data and update the UI accordingly
        taskName.setText(selectedTask.getSpaceTaskName());
        taskDes.setText(selectedTask.getTaskDetails());


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    void taskSave(ActionEvent event) {

    }
}
