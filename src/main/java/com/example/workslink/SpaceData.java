package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SpaceData {
    private ObservableList<String> spaceNamesList = FXCollections.observableArrayList();

    public ObservableList<String> getSpaceNamesList() {
        return spaceNamesList;
    }

    public void setSpaceNamesList(ObservableList<String> spaceNamesList) {
        this.spaceNamesList = spaceNamesList;
    }

}
