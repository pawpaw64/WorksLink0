package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.List;

import static java.lang.Long.toHexString;

public class SpaceCreate {
    @FXML
    private TextField space_name;
    @FXML
    private Label space_Label=new Label();
    @FXML
    Pane space_Pane1;
    String inputText;
    @FXML
    ImageView image1;
    @FXML
    ImageView image2;
    @FXML
    ImageView image3;
    @FXML
    ImageView image4;
    @FXML
    ImageView image5;
    @FXML
    ImageView image6;
    @FXML
    ImageView image7;
    @FXML
    ImageView image8;
    @FXML
    ImageView image9;
    @FXML
    ImageView image10;
    @FXML
    ImageView image11;
    @FXML
    ImageView image12;
    private void addNewItemToTreeView() {
        HomePageController homePageController=new HomePageController();
        if (homePageController != null) {
            Label newSpace = new Label();
            newSpace.setText(space_name.getText());
            homePageController.newTreeRoot(newSpace.getText());
        } else {
            System.err.println("Error: homePageController is null");
        }
    }
    public void create_spaceBtn() {
        System.out.println("jgjh");
        addNewItemToTreeView();
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
    public void setIcon(MouseEvent event){
        if (event.getSource() instanceof ImageView) {
            ImageView clickedImage = (ImageView) event.getSource();
            Image image = clickedImage.getImage();
//            space_Pane1.setStyle("-fx-background-color: #" + toHexString(circleColor));
            space_Label.setDisable(false);
            String imageUrl = image.getUrl();


            double imageViewWidth = clickedImage.getFitWidth();
            double imageViewHeight = clickedImage.getFitHeight();


            // Set the background of the Pane using CSS with the same size as the ImageView
            space_Pane1.setStyle("-fx-background-image: url('" + imageUrl + "');" +
                    "-fx-background-size: " + imageViewWidth + " " + imageViewHeight + ";" +
                    "-fx-background-repeat: no-repeat;" +
                    "-fx-background-position: center;");

            // Disable the labe


        }
    }
}