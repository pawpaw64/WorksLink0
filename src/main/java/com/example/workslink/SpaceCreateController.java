package com.example.workslink;

import com.example.workslink.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SpaceCreateController implements Initializable {


    public Button createSpaceBtn;
    @FXML
    private TextField spaceDescription;

    @FXML
    private DatePicker spaceEndDate;

    @FXML
    private TextField spaceName;

    @FXML
    private DatePicker spaceStartDate;

    public TextField getSpaceDescription() {
        return spaceDescription;
    }

    public void setSpaceDescription(TextField spaceDescription) {
        this.spaceDescription = spaceDescription;
    }

    public DatePicker getSpaceEndDate() {
        return spaceEndDate;
    }

    public void setSpaceEndDate(DatePicker spaceEndDate) {
        this.spaceEndDate = spaceEndDate;
    }

    public TextField getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(TextField spaceName) {
        this.spaceName = spaceName;
    }

    public DatePicker getSpaceStartDate() {
        return spaceStartDate;
    }

    public void setSpaceStartDate(DatePicker spaceStartDate) {
        this.spaceStartDate = spaceStartDate;
    }

    @FXML
    private Pane space_Pane1;


    public void space_circle(MouseEvent event) {
        if (event.getSource() instanceof Circle) {
            Circle clickedCircle = (Circle) event.getSource();
            Color circleColor = (Color) clickedCircle.getFill();
            space_Pane1.setStyle("-fx-background-color: #" + toHexString(circleColor));
        }
    }

    private String toHexString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return String.format("%02X%02X%02X", r, g, b);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    int userId;

    public void create_spaceBtn(ActionEvent actionEvent) {

        String sd=spaceDescription.getText();
        System.out.println(sd);

        if(getSpaceName().getText()!=null||getSpaceDescription().getText().isEmpty()||spaceStartDate.getValue()!=null||
            spaceEndDate.getValue()!=null) {
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();

                //String sql = "INSERT INTO user (email, userName,dob, password,questions,answer) VALUES (?, ?, ?, ?,?,?)";
                String sql = "INSERT INTO space_info(user_id, space_name, Space_description, start_date, end_date) VALUES(?,?,?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, String.valueOf(userId));
                    preparedStatement.setString(2, getSpaceName().getText());
                    preparedStatement.setString(3, spaceDescription.getText());
                    preparedStatement.setString(4, String.valueOf(Date.valueOf(spaceStartDate.getValue())));
                    preparedStatement.setString(5, String.valueOf(Date.valueOf(spaceEndDate.getValue())));

                    int rowsInserted = preparedStatement.executeUpdate();


                    preparedStatement.close();
                    connection.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("hhh");
        }
        Stage stage=(Stage) createSpaceBtn.getScene().getWindow();
        stage.close();
    }


    public void setUserID(int id) {
        this.userId=id;
    }
    @FXML
    private ImageView homeButton;

    public void goBack(MouseEvent mouseEvent) {

        Stage stage = (Stage) homeButton.getScene().getWindow();

            stage.close();

    }
}