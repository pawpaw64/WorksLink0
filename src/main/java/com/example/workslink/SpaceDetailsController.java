package com.example.workslink;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    @FXML
    private TableView<SpaceMembersList> membersTableView;
    @FXML
    private TableColumn<SpaceMembersList,String> membersTableColumn;

    @FXML
    private PieChart statusPieChart;
    String members;
    @FXML
    Label startDateLabel;
    @FXML
    Label endDateLabel;
    @FXML
    Label remaindaysLabel;
    private TextArea textArea;
    @FXML
    private Pane spaceDettails;

    @FXML
    private VBox CompleteVbox;
    @FXML
    private VBox doingVbox;
    @FXML
     VBox memberVbox;
    private int userId;
    String spaceName;
    String  spaceDes;
    String spaceId;
    @FXML
    private Label space_description;
    @FXML
    private Label areaSpaceName;

    @FXML
    private TableColumn<TaskHistoryData,String> taskID;
    private String start_date;
    private String end_date;
    private String estimatedDays;

    public VBox getMemberVbox() {
        return memberVbox;
    }
    @FXML
    private Button addTask;

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
           // addTaskController.setUserId(userId);

            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private TableColumn<TaskHistoryData,String> taskAction;

    public Label getSpace_description() {
        return space_description;
    }
    public void setSpace_description(Label space_description) {
        this.space_description.setText(spaceDes);
    }

    public void setAreaSpaceName(String areaSpaceName) {
        this.areaSpaceName.setText( areaSpaceName);
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


        taskName.setCellValueFactory(new PropertyValueFactory<>("spaceTaskName"));
        taskPriority.setCellValueFactory(new PropertyValueFactory<>("taskPriority"));
        taskAssigned.setCellValueFactory(new PropertyValueFactory<>("taskAssigned"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));

        OverViewDispaly();

        statusPieChart.setTitle("Task Status");
        updateStatusPieChart();
    }


    private void getSpaces() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT space_name, space_description,members,start_date,end_date,calcDays FROM space_info WHERE space_Id = "+spaceId;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                spaceName = rs.getString("space_name");
                spaceDes  = rs.getString("space_description");
                start_date  = rs.getString("start_date");
                end_date  = rs.getString("end_date");
                members = rs.getString("members");
                estimatedDays = rs.getString("calcDays");
                space_description.setText(spaceDes);
                startDateLabel.setText(start_date);
                endDateLabel.setText(end_date);
                remaindaysLabel.setText(estimatedDays);

                SpaceMembersList spaceMembersList = new SpaceMembersList(members);
                for (String s: spaceMembersList.getMembersList()){
                    memberVbox.getChildren().add(new Label(s));
                }
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    TaskHistoryData taskHistoryData;
    String  nameOfTask,statusOfTask,assignedOfTask;
    private void getSpaceTableData() {
        spaceTableView.getItems().clear();

        List<Label> todoLabels = new ArrayList<>();
        List<Label> doingLabels = new ArrayList<>();
        List<Label> completeLabels = new ArrayList<>();
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT task_name, task_description, status, priority, assigned FROM task_info WHERE space_Id= " + spaceId;
            ResultSet rs = statement.executeQuery(sql);

            // Check if there is no data
            if (!rs.isBeforeFirst()) {
                spaceTableView.setPlaceholder(new Label("No tasks added"));
            } else {
                while (rs.next()) {
                    String task_name = rs.getString("task_name");
                    String task_details = rs.getString("task_description");
                    String  status = rs.getString("status");
                    String priority = rs.getString("priority");
                    String assignedto = rs.getString("assigned");

                    nameOfTask = task_name;
                    statusOfTask = status;
                    assignedOfTask = assignedto;


                    taskHistoryData = new TaskHistoryData(task_name, status, priority, task_details, assignedto);
                    spaceTableView.getItems().add(taskHistoryData);
                    OverViewDispaly();

                    if (status.equals("To Do")) {
                        todoLabels.add(createLabel(task_name, priority, assignedto));
                    } else if (status.equals("In Progress")) {
                        doingLabels.add(createLabel(task_name, priority, assignedto));
                    } else if (status.equals("Done")) {
                        completeLabels.add(createLabel(task_name, priority, assignedto));
                    }
                }
            }
            todoVbox.getChildren().addAll(todoLabels);
            doingVbox.getChildren().addAll(doingLabels);
            CompleteVbox.getChildren().addAll(completeLabels);

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateStatusPieChart();

    }
    private Label createLabel(String task_name, String priority, String assignedto) {
        return new Label("\nTask Name: " + task_name
                + "\nPriority: " + priority + "\nAssigned: " + assignedto + "\n");
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
        getSpaces();
        getSpaceTableData();

        Callback<TableColumn<TaskHistoryData, String>, TableCell<TaskHistoryData, String>> cellFactory =
                (TableColumn<TaskHistoryData, String> param) -> {
                    // make cell containing buttons
                    final TableCell<TaskHistoryData, String> cell = new TableCell<>() {

                        final Button action = new Button("Action");
                        final Button complete=new Button("Complete");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            //that cell created only on non-empty rows
                            if (empty) {
                                setGraphic(null);
                                setText(null);


                            } else {
                                action.setOnAction((ActionEvent event) -> {
                                    TaskHistoryData selectedTask = getTableView().getItems().get(getIndex());
                                    System.out.println(selectedTask.getTaskPriority());
                                    openTaskAction(selectedTask);
                                });
                                HBox managebtn = new HBox(action, complete);
                                managebtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(action, new Insets(2, 2, 0, 3));
                                HBox.setMargin(complete, new Insets(2, 3, 0, 2));

                                setGraphic(managebtn);
                            }
                            spaceTableView.setEditable(false);
                        }


                    };
                    return cell;
                };


        taskAction.setCellFactory(cellFactory);

    }

    public  void openTaskAction(TaskHistoryData t){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/taskAction.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));

            // Pass selected task data to TaskActionController
            TaskAction taskActionController = loader.getController();
            taskActionController.setTaskData(t);


            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void OverViewDispaly() {
        // Clear all VBoxes
        todoVbox.getChildren().clear();
        doingVbox.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXML/overview.fxml"));

            // Load separate instances of AnchorPane for each VBox
            //AnchorPane todoPane = loader.load();
            AnchorPane doingPane = loader.load();

            // Access the controller after loading
            OverView o = loader.getController();

            // Check if the controller is successfully obtained
            if (o != null) {
                // Set the label data using the obtained controller
                o.setLabel(nameOfTask, assignedOfTask, statusOfTask);
            } else {
                System.err.println("Failed to obtain OverView controller.");
            }


            // Add each AnchorPane to its respective VBox
           // todoVbox.getChildren().add(doingPane);
            //doingVbox.getChildren().add(doingPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromAssignee(boolean is) {
        addTask.setDisable(true);
        this.is=is;
    }
    boolean is;

    private void updateStatusPieChart() {
        int todoCount = 0;
        int doingCount = 0;
        int completeCount = 0;

        // Iterate through tasks and count their statuses
        for (TaskHistoryData task : spaceTableView.getItems()) {
            String status = task.getTaskStatus();
            switch (status) {
                case "To Do":
                    todoCount++;
                    break;
                case "In Progress":
                    doingCount++;
                    break;
                case "Done":
                    completeCount++;
                    break;
                // Add more cases if you have additional statuses
            }
        }

        // Create PieChart.Data objects
        PieChart.Data todoData = new PieChart.Data("To Do", todoCount);
        PieChart.Data doingData = new PieChart.Data("In Progress", doingCount);
        PieChart.Data completeData = new PieChart.Data("Done", completeCount);

        // Add labels to the PieChart.Data objects
        todoData.nameProperty().set("To Do: " + todoCount);
        doingData.nameProperty().set("In Progress: " + doingCount);
        completeData.nameProperty().set("Done: " + completeCount);

        statusPieChart.getData().clear();
        statusPieChart.getData().addAll(todoData, doingData, completeData);
    }


}

