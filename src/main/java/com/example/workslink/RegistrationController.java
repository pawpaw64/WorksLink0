package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class RegistrationController extends HelloController{
    @FXML
    TextField su_email_TextField= new TextField();
    @FXML
    TextField su_username_TextFIeld= new TextField();
    @FXML
    TextField su_bdate_TextField= new TextField();
    @FXML
    TextField su_password= new TextField();
    @FXML
    Label su_valid_label = new Label();
    @FXML
    Label valid_label=new Label();
    @FXML
    TextField login_username = new TextField();
    @FXML
    TextField login_password = new TextField();

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
        email=su_email_TextField.getText();
        user=su_username_TextFIeld.getText();
        dob=su_bdate_TextField.getText();
        pass=su_password.getText();
        if(email.isEmpty()||user.isEmpty()||dob.isEmpty()||pass.isEmpty())
        {
            su_valid_label.setText("Enter All Information");
        }

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

                su_valid_label.setText("User registration successful!");
            }


            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }

    }
    @FXML
    public void login(ActionEvent ae) throws Exception {
        String userLogin = login_username.getText();
        String passLogin = login_password.getText();

        if (userLogin.isEmpty() || passLogin.isEmpty()) {
            valid_label.setText("Please Enter valid Info");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String conUrl = "jdbc:mysql://localhost:3306/registration";
                String username = "root";
                String password = "";

                try (Connection con = DriverManager.getConnection(conUrl, username, password)) {
                    String query = "SELECT * FROM email WHERE userName = ? AND password = ?";

                    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                        preparedStatement.setString(1, userLogin);
                        preparedStatement.setString(2, passLogin);

                        try (ResultSet rs = preparedStatement.executeQuery()) {
                            if (rs.next()) {
                                // Retrieve additional information
                                String Email = rs.getString("email");
                               String DOB = rs.getString("dob");
                               String USERname = rs.getString("userName");

                               //ProfileController profileController = new ProfileController(USERname,Email,DOB);
                               //profileController.profileInfo(USERname,Email,DOB);

                                valid_label.setText("Successfully logged in");
                                System.out.println(Email+DOB+USERname+"I am from RegistrationController");

                                // Jump to the homepage...
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/homePage-view.fxml"));
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);

                                Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.setY(15);
                                stage.setX(100);
                                stage.show();
                                ProfileController profileController = new ProfileController(USERname,Email,DOB);
                                profileController.setInfo();

                            } else {
                                valid_label.setText("Invalid Id or Password!");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception cE) {
                System.out.println("Class Not Found Exception: " + cE.toString());
                cE.getMessage();
            }
        }
    }
}






