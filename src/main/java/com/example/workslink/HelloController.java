package com.example.workslink;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button already_btn;

    @FXML
    private Button create_btn;

    @FXML
    private AnchorPane home_page;

    @FXML
    private Button login_btn;

    @FXML
    private AnchorPane login_page;

    @FXML
    private Button signUp_btn;

    @FXML
    private AnchorPane signUp_page;
//    @FXML
//    Button start_btn;
//    @FXML
//    Stage stage;
//    @FXML
//
//    Scene scene;
//    @FXML
//    Parent root;
//    @FXML
//    private void Start_btnAction(ActionEvent e) throws IOException {
//        //Jump in the homepage...
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXML/loginRegistration.fxml"));
//        root = fxmlLoader.load();
//        scene = new Scene(root);
////        stage.initStyle(StageStyle.UNDECORATED);
//        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//
//    }

    @FXML
    private void switchPage(ActionEvent e) {
        TranslateTransition slider = new TranslateTransition();

        if (e.getSource() == create_btn) {
            slider.setNode(home_page);
            slider.setToX(389);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent event) -> {

            });

            slider.play();
        } else if (e.getSource() == already_btn) {
            slider.setNode(home_page);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent event) -> {


            });

            slider.play();
        }


    }
    @FXML
    Button closeButton;
    public void closeOnAction(ActionEvent e){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
