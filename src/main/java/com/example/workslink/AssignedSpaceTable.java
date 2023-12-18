package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class AssignedSpaceTable {
    private String assignedSpaceName;
    private String spaceOwnerTableCol;

    public AssignedSpaceTable(String assignedSpaceName, String spaceOwnerTableCol) {
        this.assignedSpaceName = assignedSpaceName;
        this.spaceOwnerTableCol = spaceOwnerTableCol;
    }

    public String getAssignedSpaceName() {
        return assignedSpaceName;
    }

    public void setAssignedSpaceName(String assignedSpaceName) {
        this.assignedSpaceName = assignedSpaceName;
    }

    public String getSpaceOwnerTableCol() {
        return spaceOwnerTableCol;
    }

    public void setSpaceOwnerTableCol(String spaceOwnerTableCol) {
        this.spaceOwnerTableCol = spaceOwnerTableCol;
    }

    @Override
    public String toString() {
        return "AssignedSpaceTable{" +
                "assignedSpaceName='" + assignedSpaceName + '\'' +
                ", spaceOwnerTableCol='" + spaceOwnerTableCol + '\'' +
                '}';
    }
}
