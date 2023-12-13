//add_task.fxml controller
package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddTaskController implements Initializable {
    @FXML
    private ChoiceBox<?> taskAssigned;

    @FXML
    private DatePicker taskDate;
    @FXML
    private TextField taskDescription;

    @FXML
    private TextField taskName;

    @FXML
    private ChoiceBox<String> taskPriority;

    @FXML
    private ChoiceBox<String> taskStatus;


    public ChoiceBox<String> getAssignMember() {
        return assignMember;
    }

    public void setAssignMember(ChoiceBox<String> assignMember) {
        this.assignMember = assignMember;
    }

    @FXML
    private ChoiceBox<String> assignMember;


    private int taskID;
    @FXML
    private Label valid_label;
    @FXML
    private Label invalid_date_label;


    String[] priority={"Urgent","Averge","Minor"};
    String [] status={"ToDo","Ongoing","Complete"};

    @FXML
    private Button closeButton;
    private int spaceId;


    public ChoiceBox<String> getTaskAssigned() {
        return (ChoiceBox<String>) taskAssigned;
    }
    public DatePicker getTaskDate() {
        return taskDate;
    }

    public ChoiceBox<?> getTaskPriority() {
        return taskPriority;
    }
    public ChoiceBox<?> getTaskStatus() {
        return taskStatus;
    }
    String name,description,statuss,priorityy,date,assigned;



    @FXML
    private void saveTaskOnAction(ActionEvent e){
        name = taskName.getText();
        description = taskDescription.getText();
        date = String.valueOf(getTaskDate().getValue());
        priorityy = (String) getTaskPriority().getValue();
        statuss = (String) getTaskStatus().getValue();
        assigned=getAssignMember().getValue();

        if(name.isEmpty() || description.isEmpty() || date.isEmpty()){
            valid_label.setText("Enter All Information");
        }
        else {

            try{
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();
                String sql = "INSERT INTO task_info(space_Id,task_name,task_description,task_start_date,priority,status,assigned)VALUES (?,?, ?,?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, String.valueOf(spaceId));
                    preparedStatement.setString(2,name);
                    preparedStatement.setString(3,description);
                    preparedStatement.setString(4,date);
                    preparedStatement.setString(5,priorityy);
                    preparedStatement.setString(6,statuss);
                    preparedStatement.setString(7,assigned);
                    System.out.println(name+description+date);
                    System.out.println("Task Added");

                    preparedStatement.executeUpdate();


                }
            }catch (SQLException eee){
                eee.printStackTrace();
            }
        }Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    private void goBack(MouseEvent event) throws IOException {
        // Pass the user information back to the HomePageController
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    String userID;

    private List<String> getUserNamesFromDatabase() {
        List<String> userNames = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {

            String sql = "SELECT userName FROM members WHERE userID="+userID;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String userName = resultSet.getString("userName");
                    userNames.add(userName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userNames;
    }

    public void setSpaceID(int spaceId) {
        this.spaceId=spaceId;
    }

    public void setUserId(int userId) {
        this.userID= String.valueOf(userId);
        taskName.getText();
        taskPriority.getItems().addAll(priority);
        taskStatus.getItems().addAll(status);


        // Initialize the assignMember ChoiceBox with userNames
        List<String> userNames = getUserNamesFromDatabase();
        assignMember.setItems(FXCollections.observableArrayList(userNames));
    }
}