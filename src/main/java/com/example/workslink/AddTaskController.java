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

    public void setTaskAssigned(ChoiceBox<?> taskAssigned) {
        this.taskAssigned = taskAssigned;
    }

    public DatePicker getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(DatePicker taskDate) {
        this.taskDate = taskDate;
    }

    public TextField getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(TextField taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TextField getTaskName() {
        return taskName;
    }

    public void setTaskName(TextField taskName) {
        this.taskName = taskName;
    }

    public ChoiceBox<?> getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(ChoiceBox<?> taskPriority) {
        this.taskPriority = (ChoiceBox<String>) taskPriority;
    }

    public ChoiceBox<?> getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(ChoiceBox<?> taskStatus) {
        this.taskStatus = (ChoiceBox<String>) taskStatus;
    }
    String name,description,statuss,priorityy,date,assigned;



    @FXML
    private void saveTaskOnAction(ActionEvent e){
        name = taskName.getText();
        description = taskDescription.getText();
        date = String.valueOf(getTaskDate().getValue());
        priorityy = (String) getTaskPriority().getValue();
        statuss = (String) getTaskStatus().getValue();

        if(name.isEmpty() || description.isEmpty() || date.isEmpty()){
            valid_label.setText("Enter All Information");
        }
        else {

            try{
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();
                String sql = "INSERT INTO task_info(space_Id,task_name,task_description,task_start_date,priority,status)VALUES (?,?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, String.valueOf(spaceId));
                    preparedStatement.setString(2,name);
                    preparedStatement.setString(3,description);
                    preparedStatement.setString(4,date);
                    preparedStatement.setString(5,priorityy);
                    preparedStatement.setString(6,statuss);
                    System.out.println(name+description+date);

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
        taskName.getText();
        taskPriority.getItems().addAll(priority);
        taskStatus.getItems().addAll(status);


        // Initialize the assignMember ChoiceBox with userNames
        List<String> userNames = getUserNamesFromDatabase();
        assignMember.setItems(FXCollections.observableArrayList(userNames));

    }
    @FXML
    private void goBack(MouseEvent event) throws IOException {
        // Pass the user information back to the HomePageController
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    private List<String> getUserNamesFromDatabase() {
        List<String> userNames = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT userName FROM members";
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
}