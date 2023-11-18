package com.example.workslink;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class HomePageController extends RegistrationController {
    @FXML
    private Pane sidePane;
    @FXML
    public void initialize(){
    }
    private User currentUser;

    // Method to set the user from the LoginController
    public void setUser(User user) {
        this.currentUser = user;

        // Now you can use currentUser.getEmail(), currentUser.getUserName(), etc.
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
            // Pass the user information to the ProfileController
            ProfileController profileController = loader.getController();
            profileController.setUserProfile(currentUser);

            sidePane.setPrefWidth(newView.getPrefWidth());

            sidePane.getChildren().setAll(newView);
            sidePane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
}

