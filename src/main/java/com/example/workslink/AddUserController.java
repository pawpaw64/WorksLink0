package com.example.workslink;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {
    @FXML
    private ChoiceBox<String> choices;
    @FXML
    private ChoiceBox<String> role;
    private String[] gender= {"Male","Female","Others"};
    private String[] projects;

    @FXML
    private String[] roles = {"Project Manager:","Team Lead","Developer/Programmer","Tester/Quality Assurance (QA) Analys"
    ,"UI/UX Designer"+"UI/UX Designer"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choices.getItems().addAll(gender);
       role.getItems().addAll(roles);
    }
    @FXML
    private void goBack(MouseEvent event) throws IOException {
        // Pass the user information back to the HomePageController
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }




}
