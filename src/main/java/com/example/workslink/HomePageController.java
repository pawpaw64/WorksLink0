package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;



public class HomePageController extends RegistrationController implements Initializable {
    @FXML
    public Pane rightPane;
    @FXML
    public ImageView profileImg;
    public ImageView apps;
    public ImageView add_workspace;
    @FXML
    private Pane sidePane;
    private User currentUser;

    // Method to set the user from the LoginController
    public void setUser(User user) {
        this.currentUser = user;

    }

    public HomePageController() {

    }


    @FXML
    private void showProfile() {
        loadNewView("FXML/profile.fxml");
    }

    private void loadNewView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane newView = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setSidePane(sidePane);
            profileController.setProfileImg(profileImg);
            profileController.setUserProfile(currentUser);

            sidePane.setPrefWidth(newView.getPrefWidth());

            sidePane.getChildren().setAll(newView);
            sidePane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private VBox createListViewVBox(List<String> items ,int height, int width) {
        VBox vbox = new VBox();
        vbox.setPrefHeight(height);
        vbox.setPrefWidth(width);
        ListView<String> listView = new ListView<>();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        listView.setItems(observableItems);
        vbox.getChildren().add(listView);
        return vbox;
    }
    public void showApps(MouseEvent event) {
        // Create a list of items
        List<String> items = List.of("Calculator", "Notes", "More");

        // Create a VBox with a ListView
        VBox vbox = createListViewVBox(items,174,200);

        // Create a PopOver with the VBox as its content
        PopOver popOver = new PopOver(vbox);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        // Convert local coordinates to screen coordinates
        double screenX = apps.getX();
        double screenY = apps.getY();

        // Show the PopOver at the position of the apps ImageView
        popOver.show(apps, screenX, screenY);
    }
}


