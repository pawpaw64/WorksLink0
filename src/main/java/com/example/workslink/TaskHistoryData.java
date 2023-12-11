package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class TaskHistoryData {
        private SimpleStringProperty spaceTaskName;
    private SimpleStringProperty taskStatus;
    private SimpleStringProperty taskID;

    public String getTaskID() {
        return taskID.get();
    }

    public SimpleStringProperty taskIDProperty() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID.set(taskID);
    }

    public String getTaskAction() {
        return taskAction.get();
    }

    public SimpleStringProperty taskActionProperty() {
        return taskAction;
    }

    public void setTaskAction(String taskAction) {
        this.taskAction.set(taskAction);
    }

    private SimpleStringProperty taskAction;

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

    private SimpleStringProperty taskAssigned;
        private SimpleStringProperty taskPriority;

    public TaskHistoryData(String taskName, String status, String priority, String taskDetails, String assignedto) {
        this.taskAssigned=new SimpleStringProperty(assignedto);
        this.taskPriority=new SimpleStringProperty(priority);
        this.taskStatus=new SimpleStringProperty(status);
        this.spaceTaskName=new SimpleStringProperty(taskName);
    }




}
