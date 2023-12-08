package com.example.workslink;

import com.example.workslink.DatabaseConnection;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.temporal.ChronoUnit;
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
    @FXML
    private Label invalid_date_label;

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

    private boolean hasValidDateRange() {
        if (spaceStartDate.getValue() == null || spaceEndDate.getValue() == null) {
            return false;
        }
        final Date startDate = Date.valueOf(spaceStartDate.getValue());
        final Date endDate = Date.valueOf(spaceEndDate.getValue());

        //If endDate is greater than startDate return true (is valid) else return false (is invalid)
        return endDate.compareTo(startDate) >= 0;
    }

    @FXML
    private void onDateChange(javafx.event.ActionEvent event) {
        if (hasValidDateRange()) {
            invalid_date_label.setText(null);
        }
        else if (spaceStartDate.getValue() == null) {
            invalid_date_label.setText("NO_START_DATE_ERROR");
        }
        else if (spaceEndDate.getValue() != null){
            invalid_date_label.setText("INVALID_END_DATE");
        }
        else if (event == null) {
            //This means the AddTaskButton button called this method and if so we must tell the user to specify the end_date
            invalid_date_label.setText("NO_END_DATE_ERROR");

        }

        if (invalid_date_label.getText() != null) {
            invalid_date_label.setVisible(true);
        } else {
            invalid_date_label.setVisible(false);
        }
    }
    private String calcDays(DatePicker start_date, DatePicker end_date) throws IOException {
        long intervalDays = (ChronoUnit.DAYS.between(start_date.getValue(), end_date.getValue()) + 1);
        return String.valueOf(intervalDays);
    }

    public void create_spaceBtn(ActionEvent actionEvent) {

        String sd=spaceDescription.getText();
        System.out.println(sd);

        if(getSpaceName().getText()!=null||getSpaceDescription().getText().isEmpty()||spaceStartDate.getValue()!=null||
            spaceEndDate.getValue()!=null) {
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();

                //String sql = "INSERT INTO user (email, userName,dob, password,questions,answer) VALUES (?, ?, ?, ?,?,?)";
                String sql = "INSERT INTO space_info(user_id, space_name, Space_description, start_date, end_date,calcDays) VALUES(?,?,?,?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, String.valueOf(userId));
                    preparedStatement.setString(2, getSpaceName().getText());
                    preparedStatement.setString(3, spaceDescription.getText());
                    preparedStatement.setString(4, String.valueOf(Date.valueOf(spaceStartDate.getValue())));
                    preparedStatement.setString(5, String.valueOf(Date.valueOf(spaceEndDate.getValue())));
                    preparedStatement.setString(6, calcDays(spaceStartDate,spaceEndDate)+"Days");


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