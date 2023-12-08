//space_details.fxml controller
package com.example.workslink;

import com.example.workslink.DatabaseConnection;
import com.example.workslink.TaskHistoryData;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SpaceDetailsController implements Initializable {
    @FXML
    TabPane tabPane;
    @FXML
    StackPane stack1 = new StackPane();
    @FXML
    Tab task;
    @FXML
    private VBox todoVbox;
    @FXML
    private TableView<TaskHistoryData> spaceTableView;
    @FXML
    private TableColumn<TaskHistoryData,String> name;
    @FXML
    private TableColumn<TaskHistoryData,String> spaceStatus;
    @FXML
    private TableColumn<TaskHistoryData,String> spaceProgress;
    @FXML
    private TableColumn<TaskHistoryData,String> spaceID;
    private TextArea textArea;
    @FXML
    private Pane spaceDettails;
    @FXML
    private VBox completeVbox;
    @FXML
    private VBox doingVbox;



    public VBox getTodoVbox() {
        return todoVbox;
    }


    @FXML
    void add_task(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/workslink/FXML/add-task.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));

            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private Label areaSpaceName;

    public Label getAreaSpaceName() {
        return areaSpaceName;
    }

    public void setAreaSpaceName(String areaSpaceName) {
        this.areaSpaceName.setText(areaSpaceName);
    }

    void addPaneToVBox(Pane pane, VBox targetVBox) {
        targetVBox.getChildren().add(pane);
    }
    @FXML
    private PieChart pieChart;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Todo",100),
                        new PieChart.Data("Doing",30),
                        new PieChart.Data("Done",20));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(),"amount",data.pieValueProperty()
                        )
                )
        );
        pieChart.getData().addAll(pieChartData);


        spaceID.setCellValueFactory(new PropertyValueFactory<>("spaceId"));
        name.setCellValueFactory(new PropertyValueFactory<>("spaceTaskName"));
        spaceStatus.setCellValueFactory(new PropertyValueFactory<>("spaceStatus"));
        spaceProgress.setCellValueFactory(new PropertyValueFactory<>("spaceProgress"));

        spaceTableView.setEditable(false);
        getSpaceTableData();

    }
    private void getSpaceTableData() {
        spaceTableView.getItems().clear();
        try {
            System.out.println("Getting Data From Database");
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT task_projectID, task_name, task_description, status, priority FROM task_info";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String task_projectID = rs.getString("task_projectID");
                String task_name = rs.getString("task_name");
                String task_details = rs.getString("task_description");
                String status = rs.getString("status");
                String priority = rs.getString("priority");

                TaskHistoryData t = new TaskHistoryData(task_projectID, task_name, status, priority, task_details);
                spaceTableView.getItems().add(t);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button homeButton;
    public void homeButtonOnAction(ActionEvent e) throws Exception{
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }

}