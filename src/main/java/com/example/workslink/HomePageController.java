package com.example.workslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;


public class HomePageController extends HelloController implements Initializable {
    @FXML
    private AnchorPane list_anchor = new AnchorPane();
    private ObservableList<Label> labelList = FXCollections.observableArrayList();
    @FXML
    public ImageView profileImg;
    public ImageView apps;
    @FXML
    private Pane sidePane;
    private User currentUser;
    @FXML
    ImageView closeHomePage;
    public VBox space_Vbox;
    String spaceName;

    public void setUser(User user) {
        this.currentUser = user;

    }
    @FXML
    private void showProfile() {
        loadNewView("FXML/profile.fxml");
    }
    private void loadNewView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane newView = loader.load();
            if (fxmlFileName.equals("FXML/profile.fxml")) {
                ProfileController profileController = loader.getController();
                profileController.setSidePane(sidePane);
                profileController.setProfileImg(profileImg);
                profileController.setUserProfile(currentUser);
            } else if (fxmlFileName.equals("FXML/calculator.fxml")) {
                CalculatorController calculatorController = loader.getController();
                calculatorController.setSidePane(sidePane);

            }

            sidePane.setPrefWidth(newView.getPrefWidth());

            sidePane.getChildren().setAll(newView);
            sidePane.setVisible(true);

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
    // Method to handle item clicks
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
    public void closeOnAction() {
        Stage stage = (Stage) closeHomePage.getScene().getWindow();
        stage.close();
    }
   public Stage stage=new Stage();
    @FXML
    void showChat(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/chatUICtoC.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientController clientController=fxmlLoader.getController();
        clientController.setUserProfile(currentUser);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
//
    }
    @FXML
    void create_space() { //mouseEvent at add space
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/space_create.fxml"));
            Parent root = loader.load();
            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));
            SpaceCreate spaceCreateController = loader.getController();
            newStage.showAndWait();

            addNewLabel();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



   @FXML TreeView<String> spaceTree=new TreeView<>();
    public SpaceData spaceData;

    public void setSpaceData(SpaceData spaceData) {
        this.spaceData = spaceData;

        System.out.printf(this.spaceData.getSpaceNamesList()+"testing...\n");
        spaceName = this.spaceData.getSpaceNamesList();
        System.out.printf(spaceName+"testing...space name\n");


        // Call initialize after setting spaceData to ensure it's not null
        initializeTreeView();
        newTree();
    }
    void newTree(){


    }

    private void initializeTreeView() {
        if (spaceData != null && spaceData.getSpaceNamesList() != null) {
            String spaceNameList = spaceData.getSpaceNamesList();
            System.out.println(spaceNameList);
            TreeItem<String> root = new TreeItem<>(spaceNameList);
            List<String> spaces = Arrays.asList("Space1", "Space2", "Space3");
            for (String space : spaces) {
                TreeItem<String> spaceItem = new TreeItem<>(space);
                root.getChildren().add(spaceItem);
            }
            spaceTree.setRoot(root);
            spaceTree.setShowRoot(true);

            spaceTree.setRoot(root);
            spaceTree.setShowRoot(true);
            space_Vbox.getChildren().add(spaceTree);
        }
        else {
            System.out.println("null");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
//        TreeItem<String>rootItem = new TreeItem("Tutorials");
//
//        TreeItem<String> webItem = new TreeItem<>("Web Tutorials");
//        webItem.getChildren().add(new TreeItem<>("HTML  Tutorial"));
//        webItem.getChildren().add(new TreeItem<>("HTML5 Tutorial"));
//        webItem.getChildren().add(new TreeItem<>("CSS Tutorial"));
//        webItem.getChildren().add(new TreeItem<>("SVG Tutorial"));
//        rootItem.getChildren().add(webItem);
//
//        TreeItem<String>javaItem = new TreeItem<>("Java Tutorials");
//        javaItem.getChildren().add(new TreeItem<>("Java Language"));
//        javaItem.getChildren().add(new TreeItem<>("Java Collections"));
//        javaItem.getChildren().add(new TreeItem<>("Java Concurrency"));
//        rootItem.getChildren().add(javaItem);
//
//        TreeView<String>treeView = new TreeView<>();
//        treeView.setRoot(rootItem);
//
//        treeView.setShowRoot(false);

    }
    public void callLaabel(){
        Label newLabel = new Label("Project: "+spaceName);
        //System.out.printf(spaceName);
        space_Vbox.getChildren().add(newLabel);
    }


    private void addNewLabel() {
        Label newLabel = new Label("Project: "+spaceName);
        //System.out.printf(spaceName);
        space_Vbox.getChildren().add(newLabel);
    }

}
