package com.example.workslink;

import com.example.workslink.chat.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;



public class HomePageController extends HelloController implements Initializable {
    @FXML
    public Pane rightPane;
    @FXML
    public ImageView profileImg;
    public ImageView apps;
    public ImageView add_workspace;
    @FXML
    private Pane sidePane;
    private User currentUser;
    @FXML
    private AnchorPane chatAncPane;

    public void setUser(User user) {
        this.currentUser = user;

    }

    public HomePageController() {

    }


    @FXML
    private void showProfile() {
        loadNewView("FXML/profile.fxml");
    }

    private void loadNewView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane newView = loader.load();
            if(fxmlFileName=="FXML/profile.fxml"){
            ProfileController profileController = loader.getController();
            profileController.setSidePane(sidePane);
            profileController.setProfileImg(profileImg);
            profileController.setUserProfile(currentUser);}
            else if(fxmlFileName=="FXML/calculator.fxml")
            {
                CalculatorController calculatorController=loader.getController();
                calculatorController.setSidePane(sidePane);

            }

            sidePane.setPrefWidth(newView.getPrefWidth());

            sidePane.getChildren().setAll(newView);
            sidePane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private VBox createListViewVBox(List<String> items ,int height, int width) {
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
    public void showApps(MouseEvent event) {
        // Create a list of items
        List<String> items = List.of("Calculator", "Notes", "More");

        // Create a VBox with a ListView
        VBox vbox = createListViewVBox(items,116,210);

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
    ImageView closeHomePage;
    public void closeOnAction(MouseEvent e){
        Stage stage = (Stage) closeHomePage.getScene().getWindow();
        stage.close();

    }
    public void chatOnAction(){
        chatAncPane = new AnchorPane();
        Client client = new Client();
        client.getroot();

    }


}


