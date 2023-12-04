//add_task.fxml controller
package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

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
    private ChoiceBox<?> taskPriority;

    @FXML
    private ChoiceBox<?> taskStatus;
    private int taskID;


    public ChoiceBox<?> getTaskAssigned() {
        return taskAssigned;
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
        this.taskPriority = taskPriority;
    }

    public ChoiceBox<?> getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(ChoiceBox<?> taskStatus) {
        this.taskStatus = taskStatus;
    }

    @FXML
    void createTask(ActionEvent event) throws SQLException {
        try {
            System.out.println("Getting Data From task");
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO `task_info`" +
                    "( `task_name`, `task_description`, " +
                    "`task_start_date`, `priority`, `status`, `assigned`) " +
                    "VALUES ('?','?','?','?','?','?')";
            ResultSet rs = statement.executeQuery(sql);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, getTaskName().getText());
                preparedStatement.setString(2, getTaskDescription().getText());
                preparedStatement.setString(3, String.valueOf(Date.valueOf(taskDate.getValue())));
                preparedStatement.setString(4, "0%");
                // preparedStatement.setString(5,question);
                // preparedStatement.setString(6,answer);


                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}