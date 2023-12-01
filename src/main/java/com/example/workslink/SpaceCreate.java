package com.example.workslink;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Long.toHexString;

public class SpaceCreate implements Initializable {
    @FXML
    private TextField space_name;
    @FXML
    private Label space_Label;
    @FXML
    Pane space_Pane1;
    String inputText;
    public SpaceData spaceData;
    ObservableList<String> spaceNameList;

    public void create_spaceBtn() {

         inputText= space_name.getText();
         if(!inputText.isEmpty()){
             char  firstChar = inputText.charAt(0);
             space_Label.setText(String.valueOf(firstChar));
         }
         else {
             return;
         }
         spaceNameList.add(inputText);
         spaceData.setSpaceNamesList(spaceNameList);

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}