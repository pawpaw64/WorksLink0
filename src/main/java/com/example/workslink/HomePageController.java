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


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;
public class HomePageController extends HelloController implements Initializable {
    @FXML
    public ImageView profileImg;
    public ImageView apps;
    @FXML
    private Pane sidePane;
    @FXML
    Button closeButton;
    private User currentUser;
    public Stage stage=new Stage();
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
    private VBox spaceBox;
   int id;
    private Connection connection;
    public void setUser(User loggedInUser) {
        this.currentUser=loggedInUser;
        id=currentUser.getUser_id();
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
    private void loadNewView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane newView = loader.load();
            if (fxmlFileName.equals("FXML/profile.fxml")) {
                ProfileController profileController = loader.getController();

            } else if (fxmlFileName.equals("FXML/calculator.fxml")) {
                CalculatorController calculatorController = loader.getController();
                calculatorController.setSidePane(sidePane);

            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    private VBox createListViewVBox(List<String> items, int height, int width) {
        VBox vbox = new VBox();
        vbox.setPrefHeight(height);
        vbox.setPrefWidth(width);
        vbox.getStylesheets().add(getClass().getResource("CSS/popOver.css").toExternalForm());
        ListView<String> listView = new ListView<>();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        listView.setItems(observableItems);
        listView.setFixedCellSize(-3);
        // Add a listener to the selection model to handle item clicks
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleItemClick(newValue); // Call a method to handle the selected item
            }
        });
        vbox.getChildren().add(listView);
        return vbox;
    }
    private void handleItemClick(String selectedItem) {
        // Add logic to perform actions based on the selected item
        switch (selectedItem) {
            case "Calculator":
                openCalculator();
                break;
            case "Notes":
                openNotes();
                break;
            case "More":
                // Handle more options
                break;
            // Add more cases as needed
        }
    }
    private void openCalculator() {
        loadNewView("FXML/calculator.fxml");
    }
    private void openNotes() {
        loadNewView("FXML/notes.fxml");
    }
    public void showApps() {
        // Create a list of items
        List<String> items = List.of("Calculator", "Notes", "More");

        // Create a VBox with a ListView
        VBox vbox = createListViewVBox(items, 116, 210);

        // Create a PopOver with the VBox as its content
        PopOver popOver = new PopOver(vbox);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        vbox.getStylesheets().add(getClass().getResource("CSS/popOver.css").toExternalForm());
        // Convert local coordinates to screen coordinates
        // Calculate the screen coordinates for the apps ImageView
        double screenX = apps.localToScreen(apps.getBoundsInLocal()).getMinX();
        double screenY = apps.localToScreen(apps.getBoundsInLocal()).getMaxY();

        // Adjust the position to place the PopOver alongside the icon
        double adjustedX = screenX - (vbox.getWidth() - apps.getBoundsInParent().getWidth()) / 2;
        double adjustedY = screenY;

        // Show the PopOver at the adjusted position
        popOver.show(apps, adjustedX, adjustedY);
    }
    @FXML
    void showChat(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/chatUICtoC.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientController clientController = fxmlLoader.getController();
        clientController.setUserProfile(currentUser);
        System.out.println("hhhh");

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
//
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
            allMembers.setUser(id,currentUser.getUserName());

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

            // Add a listener to handle item clicks
            spaceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    handleSpaceItemClick(newValue, currentUser.getUser_id()); // Call a method to handle the selected item
                }
            });


            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void handleSpaceItemClick(String newValue, int userId) {
        System.out.println(newValue);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/space_details.fxml"));
            Parent root = loader.load();

            // Pass the selected space name to the controller if needed
            SpaceDetailsController SpaceDetailsController = loader.getController();
            SpaceDetailsController.setAreaSpaceName(newValue);

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int spaceCount;
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
                System.out.println(startDate + "  " + spaceName+ "  "  + endDate+ "  " +calcDays);
                SpaceInfo singleSpace = new SpaceInfo(spaceName, startDate, endDate,calcDays);
                spaceTableView.getItems().add(singleSpace);
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
    public void closeOnAction() {
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


    }

}


