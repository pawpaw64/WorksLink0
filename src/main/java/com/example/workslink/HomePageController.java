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
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.sql.*;
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
    private TableColumn<SpaceInfo, String> AssignedSpaceName;
    @FXML
    private TableView<SpaceInfo> assignedTable;

    @FXML
    private TableColumn<SpaceInfo, String> assignedTaskOngoing;

    int spaceCount;
    String spaceid;
    int id;

    public void setUser(User loggedInUser) {
        this.currentUser = loggedInUser;
        id = currentUser.getUser_id();

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
            profileController.setProfileImg(profileImg);
            profileController.setUserProfile(currentUser);

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
    void notepad() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/notepad.fxml"));
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
            newStage.initModality(Modality.APPLICATION_MODAL);
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
                        handleSpaceItemClick(newValue, currentUser.getUser_id(), spaceid);
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

    private void handleSpaceItemClick(String newValue, int userId, String spaceid) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/space_details.fxml"));
            Parent root = loader.load();

            // Pass the selected space name to the controller if needed
            SpaceDetailsController SpaceDetailsController = loader.getController();
            SpaceDetailsController.setAreaSpaceName(newValue);
            SpaceDetailsController.setUserId(userId);
            SpaceDetailsController.setSpaceID(spaceid);

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
                System.out.println(startDate + "  " + spaceName + "  " + endDate + "  " + calcDays);
                SpaceInfo singleSpace = new SpaceInfo(spaceName, startDate, endDate, calcDays);
                spaceTableView.getItems().add(singleSpace);
            }

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
       // getVbox();
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
    }

    private void getSpaceTableData() {
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
                System.out.println(startDate + "  " + spaceName + "  " + endDate + "  " + calcDays);
                SpaceInfo singleSpace = new SpaceInfo(spaceName, startDate, endDate, calcDays);
                spaceTableView.getItems().add(singleSpace);
                String taskCount = String.valueOf(getTaskCount(spaceId));
                System.out.println(taskCount);
                if (taskCount != null) {

                    System.out.println(startDate + "  " + spaceName + "  " + endDate + "  " + calcDays);
                    SpaceInfo singleSpace = new SpaceInfo(spaceName, startDate, endDate, calcDays, taskCount);
                    spaceTableView.getItems().add(singleSpace);
                } else {
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

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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



