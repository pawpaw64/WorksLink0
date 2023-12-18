package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class TaskHistoryData {

    private SimpleStringProperty spaceTaskName;
    private SimpleStringProperty taskStatus;
    private SimpleStringProperty taskDetails;
    private SimpleStringProperty taskAssigned;
    private  SimpleStringProperty taskPriority;

    public String getSpaceTaskName() {
        return spaceTaskName.get();
    }

    public SimpleStringProperty spaceTaskNameProperty() {
        return spaceTaskName;
    }

    public void setSpaceTaskName(String spaceTaskName) {
        this.spaceTaskName.set(spaceTaskName);
    }


    public String getTaskStatus() {
        return taskStatus.get();
    }

    public SimpleStringProperty taskStatusProperty() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus.set(taskStatus);
    }

    public String getTaskDetails() {
        return taskDetails.get();
    }

    public SimpleStringProperty taskDetailsProperty() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails.set(taskDetails);
    }

    public String getTaskAssigned() {
        return taskAssigned.get();
    }

    public SimpleStringProperty taskAssignedProperty() {
        return taskAssigned;
    }

    public void setTaskAssigned(String taskAssigned) {
        this.taskAssigned.set(taskAssigned);
    }

    public String getTaskPriority() {
        return taskPriority.get();
    }

    public SimpleStringProperty taskPriorityProperty() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority.set(taskPriority);
    }

    public TaskHistoryData(String taskName, String status, String priority, String taskDetails, String assignedto) {
        this.taskAssigned=new SimpleStringProperty(assignedto);
        this.taskPriority=new SimpleStringProperty(priority);
        this.taskDetails=new SimpleStringProperty(taskDetails);
        this.taskStatus=new SimpleStringProperty(status);
        this.spaceTaskName=new SimpleStringProperty(taskName);
    }

}
