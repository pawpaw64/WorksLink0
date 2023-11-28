package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SpaceCreate {
    @FXML
    private TextField space_name;
    @FXML
    private Button create_spaceBtn;
    public Label newSpace = new Label();
    HomePageController homePageController=new HomePageController();
    private void addNewItemToListView() {
        System.out.println("Add method");
        if (homePageController != null) {
            newSpace.setText(space_name.getText());
            System.out.println(newSpace.getText());
            homePageController.addItemToListView(newSpace);
        } else {

            System.err.println("Error: homePageController is null");
        }
    }
    @FXML
    public void create_space(ActionEvent e) { //actionPerformed at create space button
   if(e.getSource()==create_spaceBtn)
        addNewItemToListView();
        System.out.println("button work");
    }
}