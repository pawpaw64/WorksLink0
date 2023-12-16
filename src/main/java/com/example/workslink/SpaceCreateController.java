package com.example.workslink;

import com.example.workslink.DatabaseConnection;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.util.List;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
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
    @FXML
    private CheckComboBox<String> membersCheckComboBox;

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






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    int userId;
    List<String> assigneName = new ArrayList<>();
    private List<String> getMemberNames() {

        try {
            System.out.println(userId);
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userName FROM members WHERE userID = ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                assigneName.add(rs.getString("userName"));

            }
        } catch (Exception e) {

            e.printStackTrace();

        }
        return assigneName;
    }



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
        String sd = spaceDescription.getText();
        System.out.println(sd);
        //String assignee=(String) assignedTo.getValue();

        if (getSpaceName().getText() != null || getSpaceDescription().getText().isEmpty() || spaceStartDate.getValue() != null ||
                spaceEndDate.getValue() != null) {
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();

                String sql = "INSERT INTO space_info(user_id, space_name, Space_description, start_date, end_date, calcDays, members) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, String.valueOf(userId));
                    preparedStatement.setString(2, getSpaceName().getText());
                    preparedStatement.setString(3, spaceDescription.getText());
                    preparedStatement.setString(4, String.valueOf(Date.valueOf(spaceStartDate.getValue())));
                    preparedStatement.setString(5, String.valueOf(Date.valueOf(spaceEndDate.getValue())));
                    preparedStatement.setString(6, calcDays(spaceStartDate, spaceEndDate) + "Days");

                    // Get selected members from CheckComboBox
                    List<String> selectedMembers = membersCheckComboBox.getCheckModel().getCheckedItems();
                    String membersString = String.join(",", selectedMembers);
                    preparedStatement.setString(7, membersString);

                    insertDataIntoAssignedSpace(getSpaceName().getText(),selectedMembers);

                    int rowsInserted = preparedStatement.executeUpdate();



                    preparedStatement.close();
                    connection.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("");
        }
        Stage stage = (Stage) createSpaceBtn.getScene().getWindow();
        stage.close();
    }


    public void setUserID(int id) throws SQLException {
        this.userId = id;
        List<String> memberNames = getMemberNames();
        membersCheckComboBox.getItems().addAll(memberNames);
    }


    @FXML
    private ImageView homeButton;

    public void goBack(MouseEvent mouseEvent) {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }

    // Method to insert data into assignedspace table
    private void insertDataIntoAssignedSpace(String spaceName, List<String> stringList) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String insertSql = "INSERT INTO assignedspace (userName, assignedSpace) VALUES (?, ?)";

        try{
            for (String str : stringList) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                    preparedStatement.setString(1, str);
                    preparedStatement.setString(2, spaceName);
                    preparedStatement.executeUpdate();
                }
            }

            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}