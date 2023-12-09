package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class TaskHistoryData {
        private String spaceTaskName;
        private String taskSatus;
        private String taskAssigned;
        private String taskPriority;

    public TaskHistoryData(String taskName, String status, String priority, String taskDetails, String assignedto) {
        this.taskAssigned=assignedto;
        this.taskPriority=priority;
        this.taskSatus=status;
        this.spaceTaskName=taskName;
    }




}
