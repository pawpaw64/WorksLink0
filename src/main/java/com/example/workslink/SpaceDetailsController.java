package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private HBox legendContainer;

    @FXML
    private TableColumn<TaskHistoryData,String> taskID;
    private String start_date;
    private String end_date;
    private String estimatedDays;
    @FXML
    private Label labelTodo;
    @FXML
    private Label labelDoing;
    @FXML
    private Label labelComplete;

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
    @FXML
    void refresh(MouseEvent event) {
        getSpaces();
        getSpaceTableData();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        taskName.setCellValueFactory(new PropertyValueFactory<>("spaceTaskName"));
        taskPriority.setCellValueFactory(new PropertyValueFactory<>("taskPriority"));
        taskAssigned.setCellValueFactory(new PropertyValueFactory<>("taskAssigned"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));

        OverViewDispaly();


        statusPieChart.setTitle("Task Status");
        updateStatusPieChart();
        createStatusLegend();
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
            System.out.println("Getting Data From Database");
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
    private Button homeButton;
    public void homeButtonOnAction(ActionEvent e) throws Exception{
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
                       // final Button complete=new Button("Complete");

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
                                HBox managebtn = new HBox(action);
                                managebtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(action, new Insets(2, 2, 0, 3));
                               // HBox.setMargin(complete, new Insets(2, 3, 0, 2));

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

        // Calculate total tasks
        int totalCount = todoCount + doingCount + completeCount;

        // Check if totalCount is not zero to avoid division by zero
        if (totalCount != 0) {
            // Calculate percentages
            double todoPercentage = ((double) todoCount / totalCount) * 100;
            double doingPercentage = ((double) doingCount / totalCount) * 100;
            double completePercentage = ((double) completeCount / totalCount) * 100;

            // Set labels with percentages
            labelTodo.setText(String.format("To Do: %.1f%%", todoPercentage));
            labelDoing.setText(String.format("In Progress: %.1f%%", doingPercentage));
            labelComplete.setText(String.format("Done: %.1f%%", completePercentage));

            // Clear previous data and add new data to the PieChart
            statusPieChart.getData().clear();
            statusPieChart.getData().addAll(
                    new PieChart.Data("To Do", todoCount),
                    new PieChart.Data("In Progress", doingCount),
                    new PieChart.Data("Done", completeCount)
            );

            // Set labels visible
            statusPieChart.setLabelsVisible(false); // Hide default labels on the PieChart
            labelTodo.setVisible(true);
            labelDoing.setVisible(true);
            labelComplete.setVisible(true);
        } else {
            // Handle the case when totalCount is zero (avoid division by zero)
            System.out.println("Total task count is zero.");
        }
    }


    private void createStatusLegend() {
        // Define colors for each status
        Color todoColor = Color.RED;
        Color doingColor = Color.ORANGE;
        Color doneColor = Color.GREEN;

        // Create circles for each status
        Circle todoCircle = createColoredCircle(todoColor);
        Circle doingCircle = createColoredCircle(doingColor);
        Circle doneCircle = createColoredCircle(doneColor);

        // Create labels for each status
        Label todoLabel = new Label("To Do ");
        Label doingLabel = new Label(" In Progress ");
        Label doneLabel = new Label(" Done");

        // Add circles and labels to legend container
        legendContainer.getChildren().addAll(
                todoCircle, todoLabel,
                doingCircle, doingLabel,
                doneCircle, doneLabel
        );
    }

    private Circle createColoredCircle(Color color) {
        Circle circle = new Circle(7);
        circle.setFill(color);
        return circle;
    }
}

