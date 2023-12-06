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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AllMembers implements Initializable {
    Stage stage;

    private String userRole;
    private int adminId;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public TableView<MembersData> membersTableView;
    public Label membersCountLabel;
    public TableColumn<MembersData, String> memberID;
    public TableColumn<MembersData, String> memberEmail;
    public TableColumn<MembersData, String> memberUserName;
    public TableColumn<MembersData, String> memberDOB;
    @FXML
    private Button homeButton;

    public TableColumn<MembersData, String> getMemberID() {
        return memberID;
    }

    public void setMemberID(TableColumn<MembersData, String> memberID) {
        this.memberID = memberID;
    }

    public TableColumn<MembersData, String> getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(TableColumn<MembersData, String> memberEmail) {
        this.memberEmail = memberEmail;
    }

    public TableColumn<MembersData, String> getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(TableColumn<MembersData, String> memberUserName) {
        this.memberUserName = memberUserName;
    }

    public TableColumn<MembersData, String> getMemberDOB() {
        return memberDOB;
    }

    public void setMemberDOB(TableColumn<MembersData, String> memberDOB) {
        this.memberDOB = memberDOB;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        memberUserName.setCellValueFactory(new PropertyValueFactory<>("memberUserName"));
        memberEmail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
        memberDOB.setCellValueFactory(new PropertyValueFactory<>("memberDOB"));

        membersTableView.setEditable(false);


        getMembersTableData();
    }
    int membersCount;

    private void getMembersTableData() {
        membersTableView.getItems().clear();
        membersCount = 0;
        try {
            System.out.println("Getting Data From SpaceInfo");
            DatabaseConnection databaseConnection=new DatabaseConnection();
            Connection connection=databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT userName, email, dob FROM user";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                membersCount++;
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String dob = rs.getString("dob");

                MembersData members = new MembersData(userName,email,dob);
                membersTableView.getItems().add(members);
            }

            statement.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        membersCountLabel.setText("Currently you have " + String.valueOf(membersCount) + " employees.");
    }



    public void homeButtonOnAction(ActionEvent e) throws Exception{
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();

    }

}
