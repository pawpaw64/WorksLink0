package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class AssignedSpaceTable  {
    private String  assignedSpace;
    private String  spaceOwnerName;

    public AssignedSpaceTable(String assignedSpace, String spaceOwnerName) {
        this.assignedSpace = assignedSpace;
        this.spaceOwnerName = spaceOwnerName;
    }

    public String getAssignedSpace() {
        return assignedSpace;
    }

    public void setAssignedSpace(String assignedSpace) {
        this.assignedSpace = assignedSpace;
    }

    public String getSpaceOwnerName() {
        return spaceOwnerName;
    }

    public void setSpaceOwnerName(String spaceOwnerName) {
        this.spaceOwnerName = spaceOwnerName;
    }

    @Override
    public String toString() {
        return "AssignedSpaceTable{" +
                "assignedSpace=" + assignedSpace +
                ", spaceOwnerName=" + spaceOwnerName +
                '}';
    }
}
