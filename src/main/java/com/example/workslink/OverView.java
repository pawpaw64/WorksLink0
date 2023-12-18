package com.example.workslink;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OverView {

    @FXML
    private Label taskNameLabel;

    @FXML
    private Label taskStatusLabel;

    @FXML
    private Label taskAssignLabel;

    public OverView(){

    }

    public Label getTaskNameLabel() {
        return taskNameLabel;
    }

    public void setTaskNameLabel(Label taskNameLabel) {
        this.taskNameLabel = taskNameLabel;
    }

    public Label getTaskStatusLabel() {
        return taskStatusLabel;
    }

    public void setTaskStatusLabel(Label taskStatusLabel) {
        this.taskStatusLabel = taskStatusLabel;
    }

    public Label getTaskAssignLabel() {
        return taskAssignLabel;
    }

    public void setTaskAssignLabel(Label taskAssignLabel) {
        this.taskAssignLabel = taskAssignLabel;
    }
    public void setLabel(String name,String assign,String status){
        taskNameLabel.setText(name);
        taskStatusLabel.setText(status);
        taskAssignLabel.setText(assign);
    }

    @Override
    public String toString() {
        return "OverView{" +
                "taskNameLabel=" + taskNameLabel +
                ", taskStatusLabel=" + taskStatusLabel +
                ", taskAssignLabel=" + taskAssignLabel +
                '}';
    }
}


