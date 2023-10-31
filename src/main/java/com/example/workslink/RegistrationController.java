package com.example.workslink;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

import java.io.IOException;

public class RegistrationController extends HelloController{
    @FXML
    TextField tf1= new TextField();
    @FXML
    TextField tf2= new TextField();
    @FXML
    TextField tf3= new TextField();
    @FXML
    TextField tf4= new TextField();
    @FXML
    Label text = new Label();
    @FXML
    Label text2 = new Label();
    @FXML
    TextField userName = new TextField();
    @FXML
    TextField passWord = new TextField();
    @FXML
    String email,user,dob,pass;
    @FXML
    Stage stage;
    @FXML

    Scene scene;
    @FXML
    Parent root;

    @FXML
    public void signup(ActionEvent event){
        email=tf1.getText();
        user=tf2.getText();
        dob=tf3.getText();
        pass=tf4.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/registration";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO email (email, userName,dob, password) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, user);
            preparedStatement.setString(3, dob);
            preparedStatement.setString(4, pass);


            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {

                text.setText("User registration successful!");
            }


            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }

    }
    @FXML
    public void login(ActionEvent ae) throws Exception{
        String userLogin = userName.getText();
        String passLogin = passWord.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String conUrl = "jdbc:mysql://localhost:3306/registration";
            String username = "root";
            String password = "";

            Connection con = DriverManager.getConnection(conUrl,username,password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `email` WHERE 1");

            while(rs.next()) {

                if(userLogin.equals(rs.getString("userName")) && passLogin.equals(rs.getString("password"))) {
                    text2.setText("Successfully logged in");

                    //Jump in the homepage...
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXML/homePage-view.fxml"));
                    root = fxmlLoader.load();
                    scene = new Scene(root);

                    stage = (Stage)((Node)ae.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
                else {

                    text2.setText("Invalid Id or Password!");
                }
                break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
            cE.getMessage();
        }

    }
    }





