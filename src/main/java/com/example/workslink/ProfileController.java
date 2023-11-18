package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    public ImageView profile_default;
    @FXML
    public ImageView profile_2;
    @FXML
    public ImageView profile_1;
    @FXML
    public Button profile_1Set;
    @FXML
    public Button profile_2Set;
    @FXML

    public ImageView back_arrowIcon;
    @FXML
    Label profileEmail = new Label();
    @FXML
    Label profileDOB = new Label();
    @FXML
    Label profileUserName = new Label();
    @FXML
    Stage stage;
    private User userProfile;

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
        updateLabels();
    }

    private void updateLabels() {
        if (userProfile != null) {
            profileUserName.setText(userProfile.getUserName());
            profileEmail.setText(userProfile.getEmail());
            profileDOB.setText(userProfile.getDob());
            System.out.println("Labels updated!");
        }
    }


    @FXML
    private void ChangeProfile(ActionEvent e) {
        if (e.getSource() == profile_1Set) {
            LoadProfile(profile_1);

        } else if (e.getSource() == profile_2Set)
            LoadProfile(profile_2);
    }

    private void LoadProfile(ImageView img) {
        profile_default.setImage(img.getImage());
    }

    public void backButton(MouseEvent e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/homePage-view.fxml"));
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);

                                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
    }
    public void logout(ActionEvent e ) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/loginRegistration.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ProfileController initialize called");
        updateLabels();
    }
}
