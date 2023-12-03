package com.example.workslink;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class SpaceCreate implements Initializable {
    @FXML
    private TextField space_name;
    @FXML
    private Label space_Label;
    @FXML
    Pane space_Pane1;
    String inputText;
    public SpaceInfo spaceData;
   public String spaceNameList;


    public void create_spaceBtn() {
        try {
            inputText = space_name.getText();
            if (!inputText.isEmpty()) {
                char firstChar = inputText.charAt(0);
                space_Label.setText(String.valueOf(firstChar));
                spaceNameList = inputText;



                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/homePage-view.fxml"));
                Parent root = loader.load();
                HomePageController homePageController = loader.getController();


            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}