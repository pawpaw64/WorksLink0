package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class TaskHistoryData {

    private final SimpleStringProperty spaceTaskName;
    private final SimpleStringProperty spaceStatus;
    private final SimpleStringProperty spaceProgress;
    private final SimpleStringProperty spaceDetails;

    public TaskHistoryData(String spaceTaskName, String spaceStatus, String spaceProgress, String spaceDetails) {
        this.spaceTaskName = new SimpleStringProperty(spaceTaskName);
        this.spaceStatus = new SimpleStringProperty(spaceStatus);
        this.spaceProgress = new SimpleStringProperty(spaceProgress);
        this.spaceDetails = new SimpleStringProperty(spaceDetails);

    }


    public String getSpaceTaskName() {
        return spaceTaskName.get();
    }

    public String getSpaceStatus() {
        return spaceStatus.get();
    }

    public String getSpaceProgress() {
        return spaceProgress.get();
    }

    public String getSpaceDetails() {
        return spaceDetails.get();
    }
}
