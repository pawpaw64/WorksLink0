package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomePageController extends HelloController implements Initializable {
    @FXML
    public ImageView profileImg;
    @FXML
    public Label nameLabel;
    public ImageView apps;
    @FXML
    Button closeButton;
    private User currentUser;
    public Stage stage = new Stage();
    @FXML
    AnchorPane homePane;
    private ProfileController profileController;
    private Stage profileStage;
    @FXML
    private ListView<String> spaceListView;

    @FXML
    private TableColumn<SpaceInfo, String> SpaceEndDate;

    @FXML
    private TableColumn<SpaceInfo, String> SpaceName;

    @FXML
    private TableColumn<SpaceInfo, String> SpaceStartDate;
    @FXML
    private TableColumn<SpaceInfo, String> time;

    @FXML
    private TableColumn<SpaceInfo, String> TaskOngoing;
    @FXML
    private TableView<SpaceInfo> spaceTableView;
    @FXML
    private TableColumn<SpaceInfo,String> AssignedSpaceName;
    @FXML
    private TableView<SpaceInfo> assignedTable;

    @FXML
    private TableColumn<SpaceInfo,String> assignedTaskOngoing;
    @FXML
    private ListView<String> assignedListView;

    int spaceCount;
    String spaceid;
    int id;
    String userNameToMatch;

    public void setUser(User loggedInUser) {
        this.currentUser = loggedInUser;
        id = currentUser.getUser_id();
        userNameToMatch = currentUser.getUserName();
        byte[] userImgData = currentUser.getUserImg();
        if (userImgData != null && userImgData.length > 0) {
            Image profileImage = new Image(new ByteArrayInputStream(userImgData));
            profileImg.setImage(profileImage);
            //profileImg.setImage(new Image("F:\\AOOP Project\\AOOP_Project\\WorksLink0\\src\\main\\resources\\com\\example\\workslink\\Icon\\profile.png"));
            System.out.println("yes");
        } else {
            // Set a default image or handle the absence of an image
            System.out.println("no");
            //profileImg.setImage(new Image("C:\\Users\\USER\\Documents\\GitHub\\WorksLink0\\src\\main\\resources\\com\\example\\workslink\\Icon\\emoji.png"));
        }


        nameLabel.setText("Welcome " + currentUser.getUserName());
        getSpaceData();
    }
    @FXML
    public void logout(ActionEvent e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/loginRegistration.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setY(15);
        stage.setX(100);
        stage.show();
    }
    @FXML
    private void showProfile() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/profile.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        if (profileController == null) {
            profileController = fxmlLoader.getController();
            profileController.setUserProfile(currentUser,profileImg);

            profileStage = new Stage();
            profileStage.setScene(scene);
            profileStage.initStyle(StageStyle.UNDECORATED);
        }
        applyBlurEffect();
        profileStage.showAndWait();
        removeBlurEffect();
    }
    private void removeBlurEffect() {
        homePane.setEffect(null); // Remove the blur effect
    }
    @FXML
    void calculator() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/calculator.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);


        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        applyBlurEffect();
        stage.showAndWait();
        removeBlurEffect();

    }
    @FXML
    void showChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/chatUICtoC.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientController clientController = fxmlLoader.getController();
        clientController.setUserProfile(currentUser);
        System.out.println("hhhh");

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void createNewSpace() { //mouseEvent at add space
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/space_create.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));
            SpaceCreateController spaceCreateController = loader.getController();
            spaceCreateController.setUserID(id);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void membersOnAction() { //mouseEvent at add space
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/allMembers.fxml"));
            Parent root = loader.load();
            // Create a new stage for the new scene
            Stage newStage = new Stage();
            //newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(new Scene(root));
            AllMembers allMembers = loader.getController();
            allMembers.setUser(id, currentUser.getUserName());

            applyBlurEffect();
            newStage.showAndWait();
            removeBlurEffect();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getSpaceVbox() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT space_name FROM space_info WHERE user_id = " + id;
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<String> spaceNames = FXCollections.observableArrayList();

            while (rs.next()) {
                spaceCount++;
                String spaceName = rs.getString("Space_name");
                spaceNames.add(spaceName);
            }

            // Close the ResultSet, but keep the statement and connection open for the next query
            rs.close();
            statement.close();
            connection.close();


            // Set the items in the ListView
            spaceListView.setItems(spaceNames);

            // Add a listener to handle item clicks
            spaceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue != null) {
                        getSpaceInfo(newValue);
                        handleSpaceItemClick(newValue, currentUser.getUser_id(), spaceid,false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Close the statement and connection after using them

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getAssignedVbox() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();

            // Surround userNameToMatch with single quotes in the SQL query
            String sql = "SELECT assignedSpace FROM assignedspace WHERE userName = '" + userNameToMatch + "'";
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<String> spaceNames = FXCollections.observableArrayList();

            while (rs.next()) {
                spaceCount++;
                String assignedSpace = rs.getString("assignedSpace");
                spaceNames.add(assignedSpace);
            }

            // Close the ResultSet, but keep the statement and connection open for the next query
            rs.close();
            statement.close();
            connection.close();

            // Set the items in the ListView
            assignedListView.setItems(spaceNames);

            assignedListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue != null) {
                        getSpaceInfo(newValue);
                        handleSpaceItemClick(newValue, currentUser.getUser_id(), spaceid,true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSpaceInfo(String value) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT space_Id FROM space_info WHERE user_id = " + id + " AND space_name = '" + value + "'";
        ResultSet rs = statement.executeQuery(sql);
        ObservableList<String> spaceNames = FXCollections.observableArrayList();

        while (rs.next()) {
            spaceid = rs.getString("Space_Id");

        }

        // Close the ResultSet, but keep the statement and connection open for the next query
        rs.close();
        statement.close();
        connection.close();


    }
    private void handleSpaceItemClick(String newValue, int userId, String spaceid,boolean isAssigned) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/space_details.fxml"));
            Parent root = loader.load();

            // Pass the selected space name and assignment status to the controller
            SpaceDetailsController spaceDetailsController = loader.getController();
            spaceDetailsController.setAreaSpaceName(newValue);
            spaceDetailsController.setUserId(userId);
            spaceDetailsController.setSpaceID(spaceid);

            // Determine whether the space is assigned or not

            if(isAssigned){
                spaceDetailsController.fromAssignee(true);

            }

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));
            applyBlurEffect();
            newStage.showAndWait();
            removeBlurEffect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        spaceTableView.getItems().clear();

        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT space_name,start_date,end_date,calcDays FROM space_info WHERE user_id = " + id;

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String spaceName = rs.getString("space_name");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                String calcDays = rs.getString("calcDays");

                SpaceInfo singleSpace = new SpaceInfo(spaceName, startDate, endDate, calcDays);
                spaceTableView.getItems().add(singleSpace);
            }

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            //String sql = "SELECT space_name FROM space_info";
            String sql = "SELECT space_name FROM space_info WHERE user_id = " + id;

            ResultSet rs = statement.executeQuery(sql);
            ObservableList<String> spaceNames = FXCollections.observableArrayList();

            while (rs.next()) {
                spaceCount++;
                String spaceName = rs.getString("Space_name");
                spaceNames.add(spaceName);
            }

            statement.close();
            connection.close();

            // Set the items in the ListView
            spaceListView.setItems(spaceNames);

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getSpaceData() {

        getSpaceTableData();
        getSpaceVbox();
        getAssignedVbox();
    }

    private void getSpaceTableData() {
        spaceTableView.getItems().clear();

        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT space_Id ,space_name,start_date,end_date,calcDays FROM space_info WHERE user_id = " + id;

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String spaceId = rs.getString("space_Id");
                String spaceName = rs.getString("space_name");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                String calcDays = rs.getString("calcDays");
                String taskCount= String.valueOf(getTaskCount(spaceId));
                System.out.println(taskCount);
                if(taskCount!=null ){
                    SpaceInfo singleSpace = new SpaceInfo(spaceName, startDate, endDate, calcDays, taskCount);
                    spaceTableView.getItems().add(singleSpace);
                }
                else{
                    SpaceInfo singleSpace = new SpaceInfo(spaceName, startDate, endDate, calcDays);
                    spaceTableView.getItems().add(singleSpace);
                }
            }

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Map<String, String> spaceIdTaskNameMap = new HashMap<>();

    private void getSpaceId() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT space_Id, task_name FROM task_info WHERE assigneeID = " + id;

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String spaceId = rs.getString("space_Id");
                String taskName = rs.getString("task_name");

                // Store the spaceId and taskName in the map
                spaceIdTaskNameMap.put(spaceId, taskName);

                // Optionally, you can process each spaceId and taskName here as needed
                System.out.println("Space ID: " + spaceId + ", Task Name: " + taskName);
            }

            // Now, spaceIdTaskNameMap contains spaceId as keys and taskName as values

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAssignedSpaceTable() {
        assignedTable.getItems().clear();
        getSpaceId();

        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT task_name FROM task_info WHERE assigneeID = " + id;

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                String taskName = rs.getString("task_name");


            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getTaskCount(String spaceId) {
        int taskCount = 0;

        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT COUNT(*) AS taskCount FROM task_info WHERE space_Id= " + spaceId;
            ResultSet rs = statement.executeQuery(sql);

            // Check if there is no data
            if (rs.next()) {
                taskCount = rs.getInt("taskCount");

            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskCount;
    }


    private void applyBlurEffect() {
        BoxBlur blur = new BoxBlur(5, 5, 3); // You can adjust the blur parameters
        homePane.setEffect(blur);
    }

    @FXML
    public void closeOnAction() {
        System.out.println("CLose");
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpaceName.setCellValueFactory(new PropertyValueFactory<>("SpaceName"));
        SpaceStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        SpaceEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        TaskOngoing.setCellValueFactory(new PropertyValueFactory<>("taskOngoing"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        spaceTableView.setEditable(false);

        assignedTaskOngoing.setCellValueFactory(new PropertyValueFactory<>("assignedTaskOngoing"));
        AssignedSpaceName.setCellValueFactory(new PropertyValueFactory<>("AssignedSpaceName"));
        assignedTable.setEditable(false);


    }

}