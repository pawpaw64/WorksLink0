package com.example.workslink;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Allmembers2Controller implements Initializable {
    public TableView<MembersData> membersTableView;
    public Label membersCountLabel;
    public TableColumn<MembersData, String> memberID;
    public TableColumn<MembersData, String> memberEmail;
    public TableColumn<MembersData, String> memberUserName;
    public TableColumn<MembersData, String> memberDOB;
    public  TableColumn<MembersData,String> membersSelected;
    @FXML
    private CheckBox selectALl;
    @FXML
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
    private CheckBox checkBox;

    @FXML
    private TableColumn<MembersData,String> sendRqst;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberUserName.setCellValueFactory(new PropertyValueFactory<>("memberUserName"));
        memberEmail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
        memberDOB.setCellValueFactory(new PropertyValueFactory<>("memberDOB"));
        membersSelected.setCellValueFactory(new PropertyValueFactory<>("select"));
        //sendRqst.setCellValueFactory(new PropertyValueFactory<>("sendRqst"));

        membersTableView.setEditable(false);


        getSelectedData();
    }

    private void getSelectedData() {


    }

    int membersCount;
    private void getMembersTableData() {

        membersTableView.getItems().clear();
        membersCount = 0;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userName, email, dob FROM user WHERE id != ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                membersCount++;
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String dob = rs.getString("dob");
                String selectValue = null;

                MembersData members = new MembersData(userName, email, dob, selectValue);
                membersTableView.getItems().add(members);

            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        membersCountLabel.setText("Total numbers of User: " + String.valueOf(membersCount));
    }
    String excludname;

    @FXML
    public void saveMembers(ActionEvent event) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO members (userID, userName, email, dob) VALUES (?, ?, ?, ?)");

            // Iterate over the rows and insert only the selected ones
            ObservableList<MembersData> allMembers = membersTableView.getItems();
            for (MembersData member : allMembers) {
                if (member.getSelect().isSelected()) {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setString(2, member.getMemberUserName());
                    preparedStatement.setString(3, member.getMemberEmail());
                    preparedStatement.setString(4, member.getMemberDOB());

                    preparedStatement.executeUpdate();
                }
            }

            preparedStatement.close();
            connection.close();

            // Refresh the table data after insertion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/allMembers.fxml"));
            Parent root = loader.load();
            AllMembers allMembersController = loader.getController();
            allMembersController.refreshData();
            allMembersController.setUser(userId,excludname);

        } catch (Exception e) {
            e.printStackTrace();
        }

   }
    @FXML
    void goBack(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
     int userId;
    public void userId(int userId, String excludename) {
        this.userId=userId;
        this.excludname=excludename;
        getMembersTableData();
    }
}
