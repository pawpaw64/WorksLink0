package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class SpaceInfo {
    private SimpleStringProperty spaceName;
    private String spaceDescription;
    private SimpleStringProperty startDate;
    private SimpleStringProperty endDate;
    private String spaceColor;
    private  int TaskOngoing;

    public int getTaskOngoing() {
        return TaskOngoing;
    }

    public void setTaskOngoing(int taskOngoing) {
        TaskOngoing = taskOngoing;
    }

    public SpaceInfo(String spaceName, String startDate, String endDate) {
        this.spaceName = new SimpleStringProperty(spaceName);
        this.endDate = new SimpleStringProperty(endDate);
        this.startDate = new SimpleStringProperty(startDate);
    }

    public String getSpaceName() {
        return spaceName.get();
    }

    public SimpleStringProperty spaceNameProperty() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName.set(spaceName);
    }

    public String getSpaceDescription() {
        return spaceDescription;
    }

    public void setSpaceDescription(String spaceDescription) {
        this.spaceDescription = spaceDescription;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }

    public SimpleStringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }
    public String getSpaceColor() {
        return spaceColor;
    }

    public void setSpaceColor(String spaceColor) {
        this.spaceColor = spaceColor;
    }
}
