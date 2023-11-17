package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProfileController extends RegistrationController{
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
    Label profileEmail = new Label();
    @FXML
    Label profileDOB = new Label();
    @FXML
    Label profileUserName = new Label();
    public ProfileController(){
        //Default constructor...
    }

    public ProfileController(String name, String dob, String email){
        this.profileDOB.setText(dob);
        this.profileEmail.setText(email);
        this.profileUserName.setText(name);
        System.out.println(name+dob+email+"I am from ProfileController");
    }
    @FXML
    private void ChangeProfile(ActionEvent e){
        if(e.getSource()==profile_1Set){
            LoadProfile(profile_1);
        }
        else if(e.getSource()==profile_2Set)
            LoadProfile(profile_2);
    }
    @FXML
    private void LoadProfile(ImageView img){
         profile_default.setImage(img.getImage());
    }
   }
