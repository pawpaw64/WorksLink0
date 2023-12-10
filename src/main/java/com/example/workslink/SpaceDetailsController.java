//space_details.fxml controller
package com.example.workslink;

import com.example.workslink.DatabaseConnection;
import com.example.workslink.TaskHistoryData;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private TableColumn<TaskHistoryData,String> taskAssigned;

    @FXML
    private TableColumn<TaskHistoryData,String> taskName;

    @FXML
    private TableColumn<TaskHistoryData,String> taskPriority;

    @FXML
    private TableColumn<TaskHistoryData,String> taskStatus;
    private TextArea textArea;
    @FXML
    private Pane spaceDettails;
    @FXML
    private VBox completeVbox;
    @FXML
    private VBox doingVbox;
    private int userId;
    String spaceName;
    String  spaceDes;
    String spaceId;
    @FXML
    private Label space_description;
    @FXML
    void add_task(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/workslink/FXML/add-task.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));
            AddTaskController addTaskController=loader.getController();
            addTaskController.setSpaceID(Integer.parseInt(spaceId));
            addTaskController.setUserId(userId);

            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private Label areaSpaceName;

    public Label getSpace_description() {
        return space_description;
    }
    public void setSpace_description(Label space_description) {
        this.space_description.setText(spaceDes);
    }

    public void setAreaSpaceName(String areaSpaceName) {
        this.areaSpaceName.setText( spaceName);
    }

    void addPaneToVBox(Pane pane, VBox targetVBox) {
        targetVBox.getChildren().add(pane);
    }
    @FXML
    private PieChart pieChart;
    @FXML
    private Label percentlebel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data>pieChartData =
                FXCollections.observableArrayList(
                  new PieChart.Data("Todo",50),
                  new PieChart.Data("Doing",30),
                  new PieChart.Data("Done",25)
                );
        pieChart.setData(pieChartData);
        pieChart.setTitle("Stats");

        pieChart.setLegendSide(Side.RIGHT);
        pieChart.setClockwise(false);

        percentlebel.setTextFill(Color.BLACK);
        percentlebel.setStyle("-fx-font: 15 arial");

        for(final PieChart.Data data : pieChart.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
//                            percentlebel.setTranslateX(event.getSceneX()- percentlebel.getLayoutX());
//                            percentlebel.setTranslateY(event.getSceneY()- percentlebel.getLayoutY());
//                            percentlebel.setText(String.valueOf(data.getPieValue())+ "%");

                            Bounds b1 = data.getNode().getBoundsInLocal();
                            double newX = (b1.getHeight()) / 2.0 + b1.getMinX();
                            System.out.println(b1.getMinX());
                            double newY = (b1.getHeight())/ 2.0 + b1.getMinY();

                            data.getNode().setTranslateX(0);
                            data.getNode().setTranslateY(0);
                            TranslateTransition tt = new TranslateTransition(
                                    Duration.millis(1500), data.getNode()
                            );
                            tt.setByX(newX);
                            tt.setByY(newY);
                            tt.setAutoReverse(true);
                            tt.setCycleCount(2);
                            tt.play();

                        }
                    });
        }
        taskName.setCellValueFactory(new PropertyValueFactory<>("spaceTaskName"));
        taskPriority.setCellValueFactory(new PropertyValueFactory<>("taskPriority"));
        taskAssigned.setCellValueFactory(new PropertyValueFactory<>("taskAssigned"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("taskSatus"));
        spaceTableView.setEditable(false);




    }


    private void getSpaces() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT space_name, space_description FROM space_info WHERE space_Id = "+spaceId;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                spaceName = rs.getString("space_name");
                spaceDes  = rs.getString("space_description");
                space_description.setText(spaceDes);
                //System.out.println(spaceName);
                //System.out.println(spaceDes);

            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getSpaceTableData() {
        System.out.println("enter");
        spaceTableView.getItems().clear();
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT task_name, task_description, status, priority,assigned FROM task_info WHERE space_Id= "+spaceId;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String task_name = rs.getString("task_name");
                String task_details = rs.getString("task_description");
                String status = rs.getString("status");
                String priority = rs.getString("priority");
                String assignedto=rs.getString("assigned");
               // System.out.println("'task"+task_details+task_name+status+priority+assignedto);



                TaskHistoryData taskHistoryData = new TaskHistoryData(task_name, status, priority, task_details,assignedto);
                spaceTableView.getItems().add(taskHistoryData);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private ImageView homeButton;
    public void homeButtonOnAction(MouseEvent event) throws Exception{
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }
    public void setUserId(int userId) {
        this.userId=userId;
    }

    public void setSpaceID(String spaceid) {
        this.spaceId= spaceid;
//        taskName.setCellValueFactory(new PropertyValueFactory<>("spaceTaskName"));
//        taskPriority.setCellValueFactory(new PropertyValueFactory<>("taskPriority"));
//        taskStatus.setCellValueFactory(new PropertyValueFactory<>("taskSatus"));
//        taskAssigned.setCellValueFactory(new PropertyValueFactory<>("taskAssigned"));
//        spaceTableView.setEditable(false);
        getSpaces();
        getSpaceTableData();

        //System.out.println(spaceId);
    }
}