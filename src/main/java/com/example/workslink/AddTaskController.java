//add_task.fxml controller
package com.example.workslink;

import com.jfoenix.controls.JFXBadge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.TaskProgressView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private int taskID;
    @FXML
    private Label valid_label;


    String[] priority={"Urgent","Averge","Minor"};
    String [] status={"ToDo","Ongoing","Complete"};

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
    String name,description,statuss,priorityy,date;


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
               // String sql = "INSERT INTO task(name,description,startDate)VALUES (?, ?, ?)";
                String sql = "INSERT INTO task_info(task_name,task_description,task_start_date,priority,status)VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1,name);
                    preparedStatement.setString(2,description);
                    preparedStatement.setString(3,date);
                    preparedStatement.setString(4,priorityy);
                    preparedStatement.setString(5,statuss);
                    System.out.println(name+description+date);

                    preparedStatement.executeUpdate();


                }
            }catch (SQLException eee){
                eee.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskName.getText();
        taskPriority.getItems().addAll(priority);
        taskStatus.getItems().addAll(status);
    }
    @FXML
    private void goBack(MouseEvent event) throws IOException {
        // Pass the user information back to the HomePageController
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}