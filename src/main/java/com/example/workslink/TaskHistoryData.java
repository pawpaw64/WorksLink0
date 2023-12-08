package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class TaskHistoryData {
    private final SimpleStringProperty spaceId;
    private final SimpleStringProperty spaceTaskName;
    private final SimpleStringProperty spaceStatus;
    private final SimpleStringProperty spaceProgress;
    private final SimpleStringProperty spaceDetails;

    public TaskHistoryData(String spaceId, String spaceTaskName, String spaceStatus, String spaceProgress, String spaceDetails) {
        this.spaceId = new SimpleStringProperty(spaceId);
        this.spaceTaskName = new SimpleStringProperty(spaceTaskName);
        this.spaceStatus = new SimpleStringProperty(spaceStatus);
        this.spaceProgress = new SimpleStringProperty(spaceProgress);
        this.spaceDetails = new SimpleStringProperty(spaceDetails);

    }

    public String getSpaceId() {
        return spaceId.get();
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

    @Override
    public String toString() {
        return "TaskHistoryData{" +
                "spaceId=" + spaceId +
                ", spaceTaskName=" + spaceTaskName +
                ", spaceStatus=" + spaceStatus +
                ", spaceProgress=" + spaceProgress +
                ", spaceDetails=" + spaceDetails +
                '}';
    }

    public String getSpaceDetails() {
        return spaceDetails.get();
    }
}
