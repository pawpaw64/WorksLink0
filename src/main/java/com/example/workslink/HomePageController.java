package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController extends ProfileController{
    @FXML
    private Pane sidePane;
    @FXML
    private void showProfile(MouseEvent e) {
        loadView("FXML/profile.fxml");

    }  //change pane after clicking mouse
    @FXML
    private void loadView(String fxmlFileName) {
        try {
            Pane newView = FXMLLoader.load(getClass().getResource(fxmlFileName));
            sidePane.getChildren().setAll(newView);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
}
