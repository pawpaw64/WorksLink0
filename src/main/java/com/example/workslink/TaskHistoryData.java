package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class TaskHistoryData {

    private final SimpleStringProperty spaceTaskName;
    private final SimpleStringProperty spaceStatus;
    private final SimpleStringProperty spaceProgress;
    private final SimpleStringProperty spaceDetails;
    private final SimpleStringProperty space_id;

    public TaskHistoryData(String spaceTaskName, String spaceStatus, String spaceProgress, String spaceDetails,String space_id) {
        this.spaceTaskName = new SimpleStringProperty(spaceTaskName);
        this.spaceStatus = new SimpleStringProperty(spaceStatus);
        this.spaceProgress = new SimpleStringProperty(spaceProgress);
        this.spaceDetails = new SimpleStringProperty(spaceDetails);
        this.space_id = new SimpleStringProperty(space_id);

    }

    @Override
    public String toString() {
        return "TaskHistoryData{" +
                "spaceTaskName=" + spaceTaskName +
                ", spaceStatus=" + spaceStatus +
                ", spaceProgress=" + spaceProgress +
                ", spaceDetails=" + spaceDetails +
                ", space_id=" + space_id +
                '}';
    }

    public String getSpaceTaskName() {
        return spaceTaskName.get();
    }

    public SimpleStringProperty spaceTaskNameProperty() {
        return spaceTaskName;
    }

    public void setSpaceTaskName(String spaceTaskName) {
        this.spaceTaskName.set(spaceTaskName);
    }

    public String getSpaceStatus() {
        return spaceStatus.get();
    }

    public SimpleStringProperty spaceStatusProperty() {
        return spaceStatus;
    }

    public void setSpaceStatus(String spaceStatus) {
        this.spaceStatus.set(spaceStatus);
    }

    public String getSpaceProgress() {
        return spaceProgress.get();
    }

    public SimpleStringProperty spaceProgressProperty() {
        return spaceProgress;
    }

    public void setSpaceProgress(String spaceProgress) {
        this.spaceProgress.set(spaceProgress);
    }

    public String getSpaceDetails() {
        return spaceDetails.get();
    }

    public SimpleStringProperty spaceDetailsProperty() {
        return spaceDetails;
    }

    public void setSpaceDetails(String spaceDetails) {
        this.spaceDetails.set(spaceDetails);
    }

    public String getSpace_id() {
        return space_id.get();
    }

    public SimpleStringProperty space_idProperty() {
        return space_id;
    }

    public void setSpace_id(String space_id) {
        this.space_id.set(space_id);
    }
}
