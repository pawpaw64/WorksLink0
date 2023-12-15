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
import java.util.*;

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


    String[] priority={"High", "Medium", "Low"};
    String [] status={"To Do", "In Progress", "Done"};

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
    String assigneeID;



    @FXML
    private void saveTaskOnAction(ActionEvent e){
        name = taskName.getText();
        description = taskDescription.getText();
        date = String.valueOf(getTaskDate().getValue());
        priorityy = (String) getTaskPriority().getValue();
        statuss = (String) getTaskStatus().getValue();
        assigned=getAssignMember().getValue();
        assigneeID=userIdSelected;



        if(name.isEmpty() || description.isEmpty() || date.isEmpty()){
            valid_label.setText("Enter All Information");
        }
        else {

            try{
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();
                String sql = "INSERT INTO task_info(space_Id,task_name,task_description,task_start_date,priority,status,assigned,assigneeID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, String.valueOf(spaceId));
                    preparedStatement.setString(2,name);
                    preparedStatement.setString(3,description);
                    preparedStatement.setString(4,date);
                    preparedStatement.setString(5,priorityy);
                    preparedStatement.setString(6,statuss);
                    preparedStatement.setString(7,assigned);
                    preparedStatement.setString(8,assigneeID);

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
    Map<String, String> userNameMap = new HashMap<>();
//    private Map<String, String> getUserNamesFromDatabase() {
//       // List<String> userNames = new ArrayList<>();
//
//
//        try (Connection connection = DatabaseConnection.getConnection()) {
//
//            String sql = "SELECT userName,id FROM members WHERE userID="+userID;
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    String userName = resultSet.getString("userName");
//                    String id = resultSet.getString("id");
//
//                    userNameMap.put(id, userName);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return userNameMap;
//    }

    private List<String> getUserNamesFromDatabase(int spaceId) {
        List<String> userNames = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT members FROM space_info WHERE space_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, spaceId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Assuming the "members" column contains a comma-separated list of usernames.
                    String members = resultSet.getString("members");
                    userNames.addAll(Arrays.asList(members.split(",")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userNames;
    }

    String userIdSelected;

//    public void setSpaceID(int spaceId) {
//    this.spaceId = spaceId;
//    taskName.getText();
//    taskPriority.getItems().addAll(priority);
//    taskStatus.getItems().addAll(status);
//
//    // Initialize the assignMember ChoiceBox with userNames for the current space
//    Map<String, String> userNameMap = getUserNamesFromDatabase(spaceId);
//    assignMember.setItems(FXCollections.observableArrayList(userNameMap.values()));
//    assignMember.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//        userIdSelected = getUserIdFromMap(userNameMap, newValue);
//    });
//}
public void setSpaceID(int spaceId) {
    this.spaceId = spaceId;
    taskName.getText();
    taskPriority.getItems().addAll(priority);
    taskStatus.getItems().addAll(status);

    // Initialize the assignMember ChoiceBox with userNames for the current space
    List<String> userNames = getUserNamesFromDatabase(spaceId);
    assignMember.setItems(FXCollections.observableArrayList(userNames));
    assignMember.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        // You can use the selected username directly or perform additional actions.
        // userIdSelected = getUserIdFromMap(userNameMap, newValue);
    });
}

    private String getUserIdFromMap(Map<String, String> userNameMap,String newValue) {
        for (Map.Entry<String, String> entry : userNameMap.entrySet()) {
            if (Objects.equals(newValue, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}