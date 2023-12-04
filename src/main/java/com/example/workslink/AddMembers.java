package com.example.workslink;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMembers implements Initializable {
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




}
