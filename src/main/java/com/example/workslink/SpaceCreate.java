package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static java.lang.Long.toHexString;

public class SpaceCreate {
    @FXML
    private TextField space_name;
    @FXML
    private Label space_Label=new Label();
    @FXML
    Pane space_Pane1;
    @FXML
    Circle circle1;
    @FXML
    Circle circle2;
    @FXML
    Circle circle3;
    @FXML
    Circle circle4;
    @FXML
    Circle circle5;
    @FXML
    Circle circle6;
    @FXML
    Circle circle7;
    @FXML
    Circle circle8;
    String inputText;

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
    public void create_spaceBtn() {

        //addNewItemToListView();
         inputText= space_name.getText();
         if(!inputText.isEmpty()){
             char  firstChar = inputText.charAt(0);
             space_Label.setText(String.valueOf(firstChar));
         }
         else {
             return;
         }
    }
    public void space_circle(MouseEvent event){
        if (event.getSource() instanceof Circle) {
            Circle clickedCircle = (Circle) event.getSource();
            Color circleColor = (Color) clickedCircle.getFill();
            space_Pane1.setStyle("-fx-background-color: #" + toHexString(circleColor));
        }
    }

    private String toHexString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return String.format("%02X%02X%02X", r, g, b);
    }
}