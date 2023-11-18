package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    @FXML
    String name,dob,email;
    @FXML
    Stage stage;
    @FXML

    Scene scene;
    @FXML
    Parent root;
    public ProfileController(){
        //Default constructor...
    }

    public ProfileController(String name, String dob, String email){

        this.name = name;
        this.dob = dob;
        this.email = email;
        System.out.println(this.name+this.dob+this.email+"I am from ProfileController");
        System.out.println("Label has been taken...");
    }
    public void setInfo(){
        profileUserName.setText(this.name);
        profileEmail.setText(this.email);
        profileDOB.setText(this.dob);
    }
    @FXML
    private void ChangeProfile(ActionEvent e){
        if(e.getSource()==profile_1Set){
            LoadProfile(profile_1);
        }
        else if(e.getSource()==profile_2Set)
            LoadProfile(profile_2);
    }
    public void logout(ActionEvent e) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/loginRegistration.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void LoadProfile(ImageView img){
         profile_default.setImage(img.getImage());
    }
   }
