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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class AllMembers implements Initializable {
    public TableView<MembersData> membersTableView;
    public Label membersCountLabel;
    public TableColumn<MembersData, String> memberID;
    public TableColumn<MembersData, String> memberEmail;
    public TableColumn<MembersData, String> memberUserName;
    public TableColumn<MembersData, String> memberDOB;

    @FXML
    private Button homeButton;
    @FXML
    void addNewMembers(ActionEvent event) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/allMembers2.fxml"));
        Parent root = fxmlLoader.load();
        Allmembers2Controller allmembers2Controller=fxmlLoader.getController();
        allmembers2Controller.userId(userId);
        Scene scene = new Scene(root);

          Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);

        stage.show();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberUserName.setCellValueFactory(new PropertyValueFactory<>("memberUserName"));
        memberEmail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
        memberDOB.setCellValueFactory(new PropertyValueFactory<>("memberDOB"));
        membersTableView.setEditable(false);


        getMembersTableData();

    }

    private void getMembersTableData() {
        membersTableView.getItems().clear();
        int membersCount = 0;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userName, email, dob FROM members WHERE userID != ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                membersCount++;
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String dob = rs.getString("dob");

                MembersData members = new MembersData(userName, email, dob);
                membersTableView.getItems().add(members);
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        membersCountLabel.setText("Total numbers of User: " + String.valueOf(membersCount));
    }

    public void homeButtonOnAction(ActionEvent e) throws Exception{
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();

    }
    int userId;

    public void userID(int id) {
        this.userId=id;
        System.out.println(userId);
    }
}
