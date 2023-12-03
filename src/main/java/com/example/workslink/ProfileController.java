package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    public ImageView profile_default;
    @FXML
    public ImageView profile_2;
    @FXML
    public ImageView profile_1;
    @FXML
    public Button profile_1Set;
    @FXML
    public Button profile_2Set;
    @FXML
    public ImageView back_arrowIcon;
    @FXML
    public ImageView bio_EditIcon;
    @FXML
    public Label label_bio;
    @FXML
    Label profileEmail = new Label();
    @FXML
    Label profileDOB = new Label();
    @FXML
    Label profileUserName = new Label();
    public User userProfile;
    private Pane sidePane;  // Reference to the sidePane in HomePageController
    private ImageView profileImg;
    private String profile_bio;


    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
        updateLabels();
    }
    private void updateLabels() {
        if (userProfile != null) {
            profileUserName.setText(userProfile.getUserName());
            profileEmail.setText(userProfile.getEmail());
            profileDOB.setText(userProfile.getDob());

            System.out.println("Labels updated!");
        }
    }
    public void setSidePane(Pane sidePane) {
        this.sidePane = sidePane;
    }
    @FXML
    private void goBack() {
        // Pass the user information back to the HomePageController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/homePage-view.fxml"));
        try {
            Pane originalHomepage = loader.load();
            HomePageController homePageController = loader.getController();
            homePageController.setUser(userProfile);// Pass the user information back
            sidePane.getChildren().clear();
            sidePane.setPrefWidth(sidePane.getMinWidth());
            sidePane.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    @FXML
    private void ChangeProfile(ActionEvent e) {
        if (e.getSource() == profile_1Set) {
            LoadProfile(profile_1);

        } else if (e.getSource() == profile_2Set)
            LoadProfile(profile_2);
    }

    private void LoadProfile(ImageView img) {

        profile_default.setImage(img.getImage());
        profileImg.setImage(img.getImage());
    }


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ProfileController initialize called");
        updateLabels();
    }


    public void setProfileImg(ImageView profileImg) {
        this.profileImg=profileImg;
    }
    @FXML
    private void edit_bio(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == bio_EditIcon) {
            System.out.println("yes");

            // Create a PopOver
            PopOver popOver = new PopOver();

            // Create a TextArea
            TextArea textArea = new TextArea();
            textArea.setPrefColumnCount(20);
            textArea.setPrefRowCount(5);

            // Create a Button to confirm the input
            Button confirmButton = new Button("OK");
            confirmButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #05373c, #0dffea);"+
            "-fx-background-radius: 5px;"+
            "-fx-text-fill: black;"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 14px;"+
            "-fx-cursor: hand");
            confirmButton.setOnAction(event -> {
                String enteredText = textArea.getText();
                label_bio.setText(enteredText);
                userProfile.setBio(enteredText);
                popOver.hide(); // Close the PopOver after confirming
            });

            // Create a VBox to hold the TextArea and Button
            VBox content = new VBox(textArea, confirmButton);
            textArea.setStyle("-fx-background-color: linear-gradient(to bottom right, #05373c 10%, #33CCCC 80%, #0dffea);" +
                    "-fx-font-family: 'Cambria';" +
                    "-fx-font-size: 14px;" +
                    "-fx-font-weight: bold;");

            // Set the content of the PopOver
            popOver.setContentNode(content);

            // Set the title of the PopOver
            popOver.setTitle("Enter Bio");

            // Show the PopOver below the bio_EditIcon
            popOver.show(bio_EditIcon);

            // Set the size of the PopOver
            popOver.setMinWidth(300);
            popOver.setMinHeight(200);
        }
    }

}
//package com.example.workslink;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.image.PixelFormat;
//import javafx.scene.image.PixelReader;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import org.controlsfx.control.PopOver;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.*;
//import java.util.ResourceBundle;
//
//public class ProfileController implements Initializable {
//    @FXML
//    public ImageView profile_default;
//    @FXML
//    public ImageView profile_2;
//    @FXML
//    public ImageView profile_1;
//    @FXML
//    public Button profile_1Set;
//    @FXML
//    public Button profile_2Set;
//    @FXML
//    public ImageView back_arrowIcon;
//    @FXML
//    public ImageView bio_EditIcon;
//    @FXML
//    public Label label_bio;
//    @FXML
//    Label profileEmail = new Label();
//    @FXML
//    Label profileDOB = new Label();
//    @FXML
//    Label profileUserName = new Label();
//    public User userProfile;
//    private Pane sidePane;
//    private ImageView profileImg;
//    private String profile_bio;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println("ProfileController initialize called");
//        updateLabels();
//    }
//
//    public void setUserProfile(User userProfile) {
//        this.userProfile = userProfile;
//        updateLabels();
//    }
//        public void logout(ActionEvent e) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/loginRegistration.fxml"));
//        Parent root = fxmlLoader.load();
//        Scene scene = new Scene(root);
//
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.setY(15);
//        stage.setX(100);
//        stage.show();
//   }
//
//    private void updateLabels() {
//        if (userProfile != null) {
//            profileUserName.setText(userProfile.getUserName());
//            profileEmail.setText(userProfile.getEmail());
//            profileDOB.setText(userProfile.getDob());
//            label_bio.setText(userProfile.getBio());
//            // Load user image
//            Image image = new Image(new ByteArrayInputStream(userProfile.getUserImg()));
//            profile_default.setImage(image);
//        }
//    }
//
//    public void setSidePane(Pane sidePane) {
//        this.sidePane = sidePane;
//    }
//
//    @FXML
//    private void goBack() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/homePage-view.fxml"));
//        try {
//            Pane originalHomepage = loader.load();
//            HomePageController homePageController = loader.getController();
//            homePageController.setUser(userProfile);
//            sidePane.getChildren().clear();
//            sidePane.setPrefWidth(sidePane.getMinWidth());
//            sidePane.setVisible(false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void ChangeProfile(MouseEvent e) {
//        FileChooser fileChooser = new FileChooser();
//        File selectedFile = fileChooser.showOpenDialog(null);
//
//        if (selectedFile != null) {
//            Image image = new Image(selectedFile.toURI().toString());
//            profile_default.setImage(image);
//
//            // Convert Image to byte array
//            byte[] imageData = convertImageToByteArray(image);
//            userProfile.setUserImg(imageData);
//
//            // Update user image in the database
//            updateUserProfileImageInDatabase(imageData);
//        }
//    }
//
//    private byte[] convertImageToByteArray(Image image) {
//        PixelReader pixelReader = image.getPixelReader();
//        int width = (int) image.getWidth();
//        int height = (int) image.getHeight();
//
//        // ARGB format
//        int[] pixels = new int[width * height];
//        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//        // Write width and height to the stream
//        byteArrayOutputStream.write((width >>> 0) & 0xFF);
//        byteArrayOutputStream.write((width >>> 8) & 0xFF);
//        byteArrayOutputStream.write((width >>> 16) & 0xFF);
//        byteArrayOutputStream.write((width >>> 24) & 0xFF);
//        byteArrayOutputStream.write((height >>> 0) & 0xFF);
//        byteArrayOutputStream.write((height >>> 8) & 0xFF);
//        byteArrayOutputStream.write((height >>> 16) & 0xFF);
//        byteArrayOutputStream.write((height >>> 24) & 0xFF);
//
//        // Write pixel data to the stream
//        for (int pixel : pixels) {
//            byteArrayOutputStream.write((pixel >>> 16) & 0xFF); // Red
//            byteArrayOutputStream.write((pixel >>> 8) & 0xFF);  // Green
//            byteArrayOutputStream.write((pixel >>> 0) & 0xFF);  // Blue
//            byteArrayOutputStream.write((pixel >>> 24) & 0xFF); // Alpha
//        }
//
//        return byteArrayOutputStream.toByteArray();
//    }
//
//
//    private void updateUserProfileImageInDatabase(byte[] imageData) {
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            String updateQuery = "UPDATE user SET user_img = ? WHERE id = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
//                preparedStatement.setBytes(1, imageData);
//                preparedStatement.setInt(2, userProfile.getUser_id());
//                preparedStatement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void edit_bio(MouseEvent mouseEvent) {
//        if (mouseEvent.getSource() == bio_EditIcon) {
//            System.out.println("yes");
//
//            PopOver popOver = new PopOver();
//            TextArea textArea = new TextArea();
//            textArea.setPrefColumnCount(20);
//            textArea.setPrefRowCount(5);
//
//            Button confirmButton = new Button("OK");
//            confirmButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #05373c, #0dffea);" +
//                    "-fx-background-radius: 5px;" +
//                    "-fx-text-fill: black;" +
//                    "-fx-font-weight: bold;" +
//                    "-fx-font-size: 14px;" +
//                    "-fx-cursor: hand");
//            confirmButton.setOnAction(event -> {
//                String enteredText = textArea.getText();
//                label_bio.setText(enteredText);
//                userProfile.setBio(enteredText);
//
//                // Update user bio in the database
//                updateUserProfileBioInDatabase(enteredText);
//
//                popOver.hide();
//            });
//
//            VBox content = new VBox(textArea, confirmButton);
//            textArea.setStyle("-fx-background-color: linear-gradient(to bottom right, #05373c 10%, #33CCCC 80%, #0dffea);" +
//                    "-fx-font-family: 'Cambria';" +
//                    "-fx-font-size: 14px;" +
//                    "-fx-font-weight: bold;");
//
//            popOver.setContentNode(content);
//            popOver.setTitle("Enter Bio");
//            popOver.show(bio_EditIcon);
//            popOver.setMinWidth(300);
//            popOver.setMinHeight(200);
//        }
//    }
//
//    private void updateUserProfileBioInDatabase(String bio) {
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            String updateQuery = "UPDATE user SET user_bio = ? WHERE id = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
//                preparedStatement.setString(1, bio);
//                preparedStatement.setInt(2, userProfile.getUser_id());
//                preparedStatement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setProfileImg(ImageView profileImg) {
//    }
//}
