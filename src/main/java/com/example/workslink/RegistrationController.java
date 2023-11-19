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

    private Connection connection;

    public RegistrationController() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signup() {
        email = su_email_TextField.getText();
        user = su_username_TextFIeld.getText();
        dob = su_bdate_TextField.getText();
        pass = su_password.getText();

        if (email.isEmpty() || user.isEmpty() || dob.isEmpty() || pass.isEmpty()) {
            su_valid_label.setText("Enter All Information");
        }

        try {
            String sql = "INSERT INTO email (email, userName,dob, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, user);
                preparedStatement.setString(3, dob);
                preparedStatement.setString(4, pass);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    su_valid_label.setText("User registration successful!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void login(ActionEvent ae) {
        String userLogin = login_username.getText();
        String passLogin = login_password.getText();

        if (userLogin.isEmpty() || passLogin.isEmpty()) {
            valid_label.setText("Please Enter valid Info");
        } else {
            try {

                String query = "SELECT * FROM email WHERE userName = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, userLogin);
                    preparedStatement.setString(2, passLogin);

                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        if (rs.next()) {
                            // Retrieve additional information
                            String email = rs.getString("email");
                            String dob = rs.getString("dob");
                            String userName = rs.getString("userName");
                            // Create a User instance
                            User loggedInUser = new User(email, userName, dob);

                            // Jump to the homepage...
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/homePage-view.fxml"));
                            Parent root = fxmlLoader.load();
                            HomePageController homePageController = fxmlLoader.getController();
                            homePageController.setUser(loggedInUser);
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.setY(15);
                            stage.setX(100);
                            stage.show();

                            valid_label.setText("Successfully logged in");
                        } else {
                            valid_label.setText("Invalid Id or Password!");


                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception cE) {
                System.out.println("Class Not Found Exception: " + cE);

            }
        }
    }

}






