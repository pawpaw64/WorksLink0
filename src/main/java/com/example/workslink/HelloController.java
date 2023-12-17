package com.example.workslink;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class HelloController {

    @FXML
    private Button already_btn;
    @FXML
    private Button create_btn;
    @FXML
    private AnchorPane home_page;
    @FXML
    public Hyperlink forget_pass;
    @FXML
    public AnchorPane forget_passPane;
    @FXML
    public AnchorPane change_passPane;
    @FXML
    private AnchorPane login_page;
    @FXML
    private ComboBox<?> su_questions;
    @FXML
    private ComboBox<?> fp_questions;
    @FXML
    public TextField su_answers;
    @FXML
    private Label change_pass_valid_label;
    @FXML
    private Label fp_valid_label;
    @FXML
    TextField su_email_TextField ;
    @FXML
    TextField su_username_TextFIeld ;
    @FXML
    DatePicker su_bdate = new DatePicker();
    @FXML
    TextField su_password = new TextField();
    @FXML
    Label su_valid_label ;
    @FXML
    Label valid_label = new Label();
    @FXML
    TextField login_username = new TextField();
    @FXML
    TextField login_password = new TextField();
    @FXML
    private TextField fp_answers;
    @FXML
    private TextField fp_username;
    @FXML
    private PasswordField confirm_pass;
    @FXML
    private PasswordField new_pass;
    private String[] questionList = {
            "What is your favorite Color?",
            "What is your favorite food?",
            "what is your birth date?"};
    @FXML
    String email, user, dob, pass,question, answer;
    private Connection connection;

    public HelloController() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchPage(ActionEvent e) {
        TranslateTransition slider = new TranslateTransition();

        if (e.getSource() == create_btn) {
            // Apply the "corners" style class to home_page
            home_page.getStyleClass().add("corners");
            // Remove the "interPanes" style class if present
            home_page.getStyleClass().remove("interPanes");
            slider.setNode(home_page);
            slider.setToX(389);
            slider.setDuration(Duration.millis(1000));



            slider.setOnFinished((ActionEvent event) -> {
                regQuestionList();
                forgotPassQuestionList();

            });

            slider.play();
        } else if (e.getSource() == already_btn) {
            // Apply the "interPanes" style class to home_page
            home_page.getStyleClass().add("interPanes");
            // Remove the "corners" style class if present
            home_page.getStyleClass().remove("corners");
            slider.setNode(home_page);
            slider.setToX(0);
            slider.setDuration(Duration.millis(1000));
            slider.setOnFinished((ActionEvent event) -> {


            });
            slider.play();
        }
    }
    @FXML
    public void regQuestionList() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_questions.setItems(listData);
    }
    @FXML
    public void backToLoginForm() {
        login_page.setVisible(true);
        forget_passPane.setVisible(false);
    }
    @FXML
    public void backToQuestionForm() {
        forget_passPane.setVisible(true);
        change_passPane.setVisible(false);
    }
    @FXML
    public void forgotPassQuestionList() {

        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_questions.setItems(listData);

    }
    @FXML
    public void switchForgotPass() {
        forget_passPane.setVisible(true);
        login_page.setVisible(false);

        forgotPassQuestionList();
    }
    @FXML
    Button closeButton;

    public void closeOnAction(ActionEvent e) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void signup() {
        email = su_email_TextField.getText();
        user = su_username_TextFIeld.getText();
        dob = String.valueOf(su_bdate.getValue());
        question=(String) su_questions.getSelectionModel().getSelectedItem();
        answer=su_answers.getText();

        pass = su_password.getText();

        if (email.isEmpty() || user.isEmpty() || dob.isEmpty() || pass.isEmpty()||question.isEmpty()||answer.isEmpty()) {
            su_valid_label.setText("Enter All Information");
            delay(su_valid_label);
        } else {
            try {
                String sql = "INSERT INTO user (email, userName,dob, password,questions,answer) VALUES (?, ?, ?, ?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, user);
                    preparedStatement.setString(3, dob);
                    preparedStatement.setString(4, pass);
                    preparedStatement.setString(5,question);
                    preparedStatement.setString(6,answer);

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        su_valid_label.setText("User registration successful!");
                        su_valid_label.setVisible(true);
                        su_email_TextField.clear();
                        su_username_TextFIeld.clear();
                        su_bdate.getEditor().clear(); // Clear the DatePicker
                        su_password.clear();
                        su_questions.getSelectionModel().clearSelection();
                        su_answers.clear();
                        delay(su_valid_label);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void login(ActionEvent ae) {
        String userLogin = login_username.getText();
        String passLogin = login_password.getText();

        if (userLogin.isEmpty() || passLogin.isEmpty()) {
            valid_label.setText("Please Enter valid Info");
            delay(valid_label);
        } else {
            try {

                String query = "SELECT * FROM user WHERE userName = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, userLogin);
                    preparedStatement.setString(2, passLogin);

                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        if (rs.next()) {
                            // Retrieve additional information
                            String email = rs.getString("email");
                            String dob = rs.getString("dob");
                            String userName = rs.getString("userName");
                            int user_id=rs.getInt("id");
                            String bio=rs.getString("user_bio");
                            byte[] user_img=rs.getBytes("user_img");
                            if (bio == null) {
                                // Set a default value or handle the case when bio is null
                                bio = "Default Bio";
                            }

                            if (user_img == null) {
                                // Set a default image or handle the case when user_img is null
                                user_img = getDefaultUserImage();
                            }
                            login_username.clear();
                            login_password.clear();
                            // Create a User instance
                            User loggedInUser = new User(email, userName, dob,user_id,bio,user_img);
                            // Jump to the homepage...v
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/homePage-view.fxml"));
                            Parent root = fxmlLoader.load();
                            HomePageController homePageController = fxmlLoader.getController();

                            homePageController.setUser(loggedInUser);
                           // homePageController.settingUserID(user_id);

                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.setY(15);
                            stage.setX(100);
                            stage.show();

                            valid_label.setText("Successfully logged in");
                        } else {
                            valid_label.setText("Invalid Id or Password!");
                            delay(valid_label);

                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception cE) {
                cE.printStackTrace();
                System.out.println("Class Not bbbbbFound Exception: " + cE);

            }
        }
    }

    private byte[] getDefaultUserImage() {
        Path imagePath = Paths.get("C:\\Users\\USER\\Documents\\GitHub\\WorksLink0\\src\\main\\resources\\com\\example\\workslink\\Icon\\emoji.png");

        try {
            byte[] imageData = Files.readAllBytes(imagePath);
            return Base64.getEncoder().encode(imageData);
        } catch (Exception e) {
            // Handle exceptions, e.g., file not found, IOException, etc.
            e.printStackTrace();
            return new byte[0]; // Return an empty byte array as a fallback
        }// Replace this with the actual default image data
        }


    public void delay(Label label) {
        Duration delay = Duration.seconds(3);
        KeyFrame keyFrame = new KeyFrame(delay, event -> label.setVisible(false));
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
 @FXML
 public void proceedBtn() {
    if (fp_username.getText().isEmpty() || fp_questions.getSelectionModel().getSelectedItem() == null
            || fp_answers.getText().isEmpty()) {
        fp_valid_label.setText("Enter All Information");
        delay(fp_valid_label);
    } else {
        try (Connection connect = DatabaseConnection.getConnection()) {
            String selectData = "SELECT userName, questions, answer FROM user WHERE userName = ? AND questions = ? AND answer = ?";
            try (PreparedStatement prepare = connect.prepareStatement(selectData)) {
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_questions.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answers.getText());

                try (ResultSet result = prepare.executeQuery()) {
                    if (result.next()) {
                        change_passPane.setVisible(true);
                        forget_passPane.setVisible(false);
                    }
                    else {
                        fp_valid_label.setText("Incorrect Information");
                        delay(fp_valid_label);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} //NEED TO CHECK
    @FXML
    public void changePassBtn() {
        if (new_pass.getText().isEmpty() || confirm_pass.getText().isEmpty()) {
            change_pass_valid_label.setText("Enter All Information");
            delay(change_pass_valid_label);
        } else {
            if (new_pass.getText().equals(confirm_pass.getText())) {
                try (Connection connect = DatabaseConnection.getConnection()) {
                    String updatePass = "UPDATE user SET password = ? WHERE userName = ?";
                    try (PreparedStatement updatePrepare = connect.prepareStatement(updatePass)) {
                        updatePrepare.setString(1, new_pass.getText());
                        updatePrepare.setString(2, fp_username.getText());

                        int rowsUpdated = updatePrepare.executeUpdate();

                        if (rowsUpdated > 0) {
                            login_page.setVisible(true);
                            change_passPane.setVisible(false);

                            // TO CLEAR FIELDS
                            confirm_pass.setText("");
                            new_pass.setText("");
                            fp_username.setText("");

                            valid_label.setText("Password changed successfully!");
                            delay(valid_label);
                        } else {
                            change_pass_valid_label.setText("User not found");
                            delay(change_pass_valid_label);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                change_pass_valid_label.setText("Password Doesn't match");
                delay(change_pass_valid_label);
            }
        }
    } //NEED TO CHECK

}




