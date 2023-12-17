package com.example.workslink;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Allmembers2Controller implements Initializable {

    @FXML
    private TableView<UserRequestData> AccReTable;
    @FXML
    private TableColumn<UserRequestData,String> Id;

    @FXML
    private TableColumn<UserRequestData,String> accept;
    @FXML
    private TableColumn<UserRequestData,String> name;

    @FXML
    private TableColumn<UserRequestData,String> reject;

    public TableView<MembersData> membersTableView;
    public Label membersCountLabel;
    public TableColumn<MembersData, Integer> membersID;
    public TableColumn<MembersData, String> memberEmail;
    public TableColumn<MembersData, String> memberUserName;
    public TableColumn<MembersData, String> memberDOB;
   public  TableColumn<MembersData,String> membersSelected;
    @FXML
    private CheckBox selectALl;
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
      //  accept.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));
        accept.setCellFactory(cellFactory -> new AcceptButtonCell());
        name.setCellValueFactory(new  PropertyValueFactory<>("name"));

       // reject.setCellValueFactory(new PropertyValueFactory<>("rejectButton"));
        reject.setCellFactory(cellFactory -> new RejectButtonCell());

        memberUserName.setCellValueFactory(new PropertyValueFactory<>("memberUserName"));
        memberEmail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
        memberDOB.setCellValueFactory(new PropertyValueFactory<>("memberDOB"));
        membersID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        Callback<TableColumn<MembersData, String>, TableCell<MembersData, String>> cellFoctory =
                (TableColumn<MembersData, String> param) -> {
                    // make cell containing buttons

                    final TableCell<MembersData, String> cell = new TableCell<>() {

                        final Button sendRequest = new Button("Send Request");
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            //that cell created only on non-empty rows
                            if (empty) {
                                setGraphic(null);
                                setText(null);

                            } else {
                                sendRequest.setOnAction((ActionEvent e)-> {
                                    MembersData member = getTableView().getItems().get(getIndex());
                                    sendRequestToUser(member);
                                });



                                setGraphic(sendRequest);
                            }
                            membersTableView.setEditable(false);
                        }
                    };
                    return cell;
                };
        sendRqst.setCellFactory(cellFoctory);

        membersSelected.setCellValueFactory(new PropertyValueFactory<>("select"));
      //  getMembersTableData();
        membersTableView.setEditable(false);
        AccReTable.setEditable(false);


    }
    private class AcceptButtonCell extends TableCell<UserRequestData, String> {
        final Button acceptButton = new Button("Accept");

        AcceptButtonCell() {
            acceptButton.setOnAction(event -> {
                UserRequestData userRequest = getTableView().getItems().get(getIndex());
                acceptRequest(userRequest);
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(acceptButton);
            }
        }
    }

    private class RejectButtonCell extends TableCell<UserRequestData, String> {
        final Button rejectButton = new Button("Reject");

        RejectButtonCell() {
            rejectButton.setOnAction(event -> {
                UserRequestData userRequest = getTableView().getItems().get(getIndex());
                rejectRequest(userRequest);
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(rejectButton);
            }
        }
    }
    private void rejectRequest(UserRequestData request) {
        // Implement the logic for rejecting the request
        // ...

        // Optionally, you can provide a notification or update the UI

        // Example: Print a message to the console
        System.out.println("Request rejected successfully!");
    }
    private void sendRequestToUser(MembersData selectedUser) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            // Insert into user_requests table
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO user_requests (sender_user_id, receiver_user_id, status) VALUES (?, ?, ?)");

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, Integer.parseInt(selectedUser.getMemberId()));
            preparedStatement.setString(3, "PENDING");

            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Insert into user_notification table with sender's username
//            preparedStatement = connection.prepareStatement(
//                    "INSERT INTO user_requests (receiver_user_id, sender_username, status) VALUES (?, ?, ?)");
//
//            preparedStatement.setInt(1, Integer.parseInt(selectedUser.getMemberId()));
//            preparedStatement.setString(2, excludname); // Assuming excludname is the sender's username
//            preparedStatement.setString(3, "PENDING");
//
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//
//            connection.close();

            // Optionally, you can update the status in the UI or provide a notification
            System.out.println("Request sent successfully!");
            showAlert(Alert.AlertType.INFORMATION, "Request Sent", "Request sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("alertPrompt.css").toExternalForm());
        dialogPane.getStyleClass().add("styled-alert");

        alert.showAndWait();
    }
    private void acceptRequest(UserRequestData request) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE user_requests SET status = 'ACCEPTED' WHERE request_id = ?");

            // Set the request ID for the update
            updateStatement.setInt(1, request.getRequestId());

            // Execute the SQL statement to update the status
            updateStatement.executeUpdate();

            // Optionally, you can provide a notification or update the UI

            // Insert data into the members table
            insertIntoMembers(request.getSenderUserId());
            System.out.println(request.getSenderUserId());

            updateStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void insertIntoMembers(int memberId) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO members (userID, id, userName, email, dob) SELECT ?, id, userName, email, dob FROM user WHERE id = ?");

            // Set the user ID for the insert
            insertStatement.setInt(1, userId);
            insertStatement.setInt(2, memberId);

            // Execute the SQL statement to insert data into the members table
            insertStatement.executeUpdate();

            // Optionally, you can provide a notification or update the UI

            insertStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkPendingRequests() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user_requests WHERE receiver_user_id = ? AND status = 'PENDING'");
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Display a notification about pending requests
                showNotification("You have pending requests", "Check your notifications for details.");

                // You may want to update the status of the requests in the database
                updateRequestStatus();
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateRequestStatus() {
        try {
            // Update the status of the requests in the database
            // For example, set status to 'SEEN'
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE user_requests SET status = 'SEEN' WHERE receiver_user_id = ?");
            updateStatement.setInt(1, userId);

            updateStatement.executeUpdate();

            updateStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotification(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    int membersCount;
    private void getAccRejTableData(){
        AccReTable.getItems().clear();
        try{
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT sender_username, status,sender_user_id,receiver_user_id FROM user_requests WHERE receiver_user_id = ? AND status = 'PENDING'");
            preparedStatement.setInt(1, userId);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Create UserNotificationData objects and add them to the list
                // You need to replace this with your actual column names
                String senderUsername = resultSet.getString("sender_username");
                String status = resultSet.getString("status");
                int sender_user_id=resultSet.getInt("senderUserId");

                UserRequestData userRequestData = new UserRequestData(senderUsername,sender_user_id,status);
                AccReTable.getItems().add(userRequestData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getMembersTableData() {

        membersTableView.getItems().clear();
        membersCount = 0;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,userName, email, dob FROM user WHERE id != ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                membersCount++;
                String id= rs.getString("id");
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String dob = rs.getString("dob");
                System.out.println("ID"+id);

                MembersData members = new MembersData(userName, email, dob, id);
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
    public void saveMembers(ActionEvent event) throws IOException {
////        try {
////            DatabaseConnection databaseConnection = new DatabaseConnection();
////            Connection connection = databaseConnection.getConnection();
////            PreparedStatement preparedStatement = connection.prepareStatement(
////                    "INSERT INTO members (userID, id ,userName, email, dob) VALUES (?,?,?, ?, ?)");
////
////            // Iterate over the rows and insert only the selected ones
////            ObservableList<MembersData> allMembers = membersTableView.getItems();
////            for (MembersData member : allMembers) {
////                if (member.getSelect().isSelected()) {
////                    preparedStatement.setInt(1, userId);
////                    preparedStatement.setString(2, member.getMemberId());
////                    preparedStatement.setString(3, member.getMemberUserName());
////                    preparedStatement.setString(4, member.getMemberEmail());
////                    preparedStatement.setString(5, member.getMemberDOB());
////
////                    preparedStatement.executeUpdate();
////                }
////            }
////
////            preparedStatement.close();
////            connection.close();
//
//            // Refresh the table data after insertion
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/allMembers.fxml"));
//            Parent root = loader.load();
//            AllMembers allMembersController = loader.getController();
//            allMembersController.refreshData();
//            allMembersController.setUser(userId,excludname);

        }

    @FXML
    void goBack(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
     int userId;
    public void userId(int userId, String excludename) {
        this.userId = userId;
        this.excludname = excludename;
        getAccRejTableData();
        getMembersTableData();
        checkPendingRequests();
    }
}

