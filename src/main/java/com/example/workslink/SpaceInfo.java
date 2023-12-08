package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class SpaceInfo {
    private SimpleStringProperty spaceName;
    private String spaceDescription;
    private SimpleStringProperty startDate;
    private SimpleStringProperty endDate;
    private SimpleStringProperty time;

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    private int assigneeId;
    private String spaceColor;

    public SpaceInfo(String spaceName, String startDate, String endDate,String time) {
        this.spaceName = new SimpleStringProperty(spaceName);
        this.endDate = new SimpleStringProperty(endDate);
        this.startDate = new SimpleStringProperty(startDate);
        this.time = new SimpleStringProperty(time);
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

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getSpaceColor() {
        return spaceColor;
    }

    public void setSpaceColor(String spaceColor) {
        this.spaceColor = spaceColor;
    }
}
