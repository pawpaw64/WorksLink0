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
import java.util.Objects;
import java.util.ResourceBundle;

public class Allmembers2Controller implements Initializable {
    @FXML
    private TableView<UserRequestData> AccReTable;
    @FXML
    private TableColumn<UserRequestData, String> accept;
    @FXML
    private TableColumn<UserRequestData, String> name;
    @FXML
    private TableColumn<UserRequestData, String> reject;
    public TableView<MembersData> membersTableView;
    public Label membersCountLabel;
    public TableColumn<MembersData, Integer> membersID;
    public TableColumn<MembersData, String> memberEmail;
    public TableColumn<MembersData, String> memberUserName;
    public TableColumn<MembersData, String> memberDOB;
      @FXML
    private TableColumn<MembersData, String> sendRqst;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //  accept.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));
        accept.setCellFactory(cellFactory -> new AcceptButtonCell());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        // reject.setCellValueFactory(new PropertyValueFactory<>("rejectButton"));
        reject.setCellFactory(cellFactory -> new RejectButtonCell());

        memberUserName.setCellValueFactory(new PropertyValueFactory<>("memberUserName"));
        memberEmail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
        memberDOB.setCellValueFactory(new PropertyValueFactory<>("memberDOB"));
        membersID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        Callback<TableColumn<MembersData, String>, TableCell<MembersData, String>> cellFoctory =
                (TableColumn<MembersData, String> param) -> {
                     final TableCell<MembersData, String> cell = new TableCell<>() {
                        final Button sendRequest = new Button("Send Request");
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);

                            } else {
                                sendRequest.setOnAction((ActionEvent e) -> {
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
                    "INSERT INTO user_requests (sender_user_id, receiver_user_id, status,sender_username) VALUES (?, ?,?, ?)");

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, Integer.parseInt(selectedUser.getMemberId()));
            preparedStatement.setString(3, "PENDING");
            preparedStatement.setString(4, excludname);

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
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("CSS/alertPrompt.css")).toExternalForm());
        dialogPane.getStyleClass().add("styled-alert");
// Get the button and apply a custom style
        alert.showAndWait();
    }

    private void acceptRequest(UserRequestData request) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            // Update the status in the user_requests table to 'ACCEPTED'
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE user_requests SET status = 'ACCEPTED' WHERE request_id = ?");
            updateStatement.setInt(1, request.getRequestId());
            System.out.println(request.getRequestId());
            updateStatement.executeUpdate();
            updateStatement.close();

            // Optionally, you can provide a notification or update the UI

            // Insert data into the members table
            insertIntoMembers(request.getSenderUserId(),request.getReceiverUserId());
            System.out.println("sender id"+request.getSenderUserId());
            System.out.println("Recevwer id"+request.getReceiverUserId());

            connection.close();

            // Refresh the data in the AccRejTable
            getAccRejTableData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void insertIntoMembers(int senderId, int receiverId) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            // Insert data for the sender
            PreparedStatement insertStatementSender = connection.prepareStatement(
                    "INSERT INTO members (userID, id, userName, email, dob) " +
                            "SELECT ?, id, userName, email, dob FROM user WHERE id = ?");
            insertStatementSender.setInt(1, senderId);
            insertStatementSender.setInt(2, senderId);
            insertStatementSender.executeUpdate();
            insertStatementSender.close();

            // Insert data for the receiver
            PreparedStatement insertStatementReceiver = connection.prepareStatement(
                    "INSERT INTO members (userID, id, userName, email, dob) " +
                            "SELECT ?, id, userName, email, dob FROM user WHERE id = ?");
            insertStatementReceiver.setInt(1, receiverId);
            insertStatementReceiver.setInt(2, receiverId);
            insertStatementReceiver.executeUpdate();
            insertStatementReceiver.close();

            // Optionally, you can provide a notification or update the UI

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

    private void getAccRejTableData() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT sender_username, status, sender_user_id,request_id, receiver_user_id FROM " +
                            "user_requests WHERE (receiver_user_id = ?) " +
                            "AND (status = 'PENDING' OR status = 'SEEN')");
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Clear previous items
            AccReTable.getItems().clear();

            while (resultSet.next()) {
                // Create UserRequestData objects and add them to the list
                // You need to replace this with your actual column names
                String senderUsername = resultSet.getString("sender_username");
                String status = resultSet.getString("status");
                int senderUserId = resultSet.getInt("sender_user_id");
                int requestID=resultSet.getInt("request_id");
                int  receieverID=resultSet.getInt("receiver_user_id");

                UserRequestData userRequestData=new UserRequestData(senderUsername,senderUserId,status,requestID,receieverID);
                AccReTable.getItems().add(userRequestData);
            }

            preparedStatement.close();
            connection.close();
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

            // Select members who are not the current user
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, userName, email, dob FROM user WHERE id != ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String memberId = rs.getString("id");
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String dob = rs.getString("dob");

                // Check if the user is already a member
                if (!isUserAlreadyMember(memberId)) {
                    membersCount++;
                    MembersData members = new MembersData(userName, email, dob, memberId);
                    membersTableView.getItems().add(members);
                }
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        membersCountLabel.setText("Total numbers of User: " +(membersCount));
    }
    private boolean isUserAlreadyMember(String memberId) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            // Check if the user is already a member
            PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM members WHERE userID = ? AND id = ?");
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, Integer.parseInt(memberId));
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

            checkStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    String excludname;

    @FXML
    public void saveMembers() throws IOException {
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

