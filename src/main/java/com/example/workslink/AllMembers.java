package com.example.workslink;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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

    public Button homeBackBtn;
    public Button AddEmployeeBtn;
    public Button viewAllEmployee;
    public TableView<Members> membersTableView;
    public Label employeeCountLabel;
    public TableColumn<Members, String> memberID;
    public TableColumn<Members, String> memberEmail;
    public TableColumn<Members, String> memberUserName;
    public TableColumn<Members, String> memberDOB;

    public TableColumn<Members, String> getMemberID() {
        return memberID;
    }

    public void setMemberID(TableColumn<Members, String> memberID) {
        this.memberID = memberID;
    }

    public TableColumn<Members, String> getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(TableColumn<Members, String> memberEmail) {
        this.memberEmail = memberEmail;
    }

    public TableColumn<Members, String> getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(TableColumn<Members, String> memberUserName) {
        this.memberUserName = memberUserName;
    }

    public TableColumn<Members, String> getMemberDOB() {
        return memberDOB;
    }

    public void setMemberDOB(TableColumn<Members, String> memberDOB) {
        this.memberDOB = memberDOB;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        memberID.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        memberEmail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
        memberUserName.setCellValueFactory(new PropertyValueFactory<>("memberUserName"));
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
            String sql = "SELECT space_name, start_date, end_date FROM space_info";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                membersCount++;
                String spaceName = rs.getString("Space_name");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                System.out.println("w"+" "+spaceName+" "+startDate+"" +endDate);

//                Members members = new Members();
//                membersTableView.getItems().add();
            }

            statement.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void ViewAllMembers(ActionEvent e) {

    }
}
