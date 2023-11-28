package com.example.workslink;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SpaceCreate {
    @FXML
    private TextField space_name;

    private HomePageController homePageController;
    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }
    private void addNewItemToListView() {
        if (homePageController != null) {

            Label newSpace = new Label();
            newSpace.setText(space_name.getText());


            homePageController.addItemToListView(newSpace);
        } else {

            System.err.println("Error: homePageController is null");
        }
    }
    public void create_space() {

        addNewItemToListView();
    }
}