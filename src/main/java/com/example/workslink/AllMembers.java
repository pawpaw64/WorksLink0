package com.example.workslink;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.w3c.dom.ls.LSOutput;

public class AllMembers implements Initializable {
    public TableView<MembersData> membersTableView;
    public Label membersCountLabel;

    public TableColumn<MembersData, String> memberEmail;
    public TableColumn<MembersData, String> memberUserName;
    public TableColumn<MembersData, String> memberDOB;

    @FXML
    private Button homeButton;
    private String excludename;

    @FXML
    private TableColumn<MembersData, String> memeberEdit;

    @FXML
    void addNewMembers(ActionEvent event) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(ClientController.class.getResource("FXML/allMembers2.fxml"));
        Parent root = fxmlLoader.load();
        Allmembers2Controller allmembers2Controller = fxmlLoader.getController();
        allmembers2Controller.userId(userId, excludename);
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();


    }

    int userId;
    Image image ;
    MembersData membersData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberUserName.setCellValueFactory(new PropertyValueFactory<>("memberUserName"));
        memberEmail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
        memberDOB.setCellValueFactory(new PropertyValueFactory<>("memberDOB"));


        Callback<TableColumn<MembersData, String>, TableCell<MembersData, String>> cellFoctory =
                (TableColumn<MembersData, String> param) -> {
                    // make cell containing buttons

                    final TableCell<MembersData, String> cell = new TableCell<>() {

                        final Button delete = new Button("Delete");
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            //that cell created only on non-empty rows
                            if (empty) {
                                setGraphic(null);
                                setText(null);


                            } else {


                                delete.setOnAction((ActionEvent event) -> {
                                    try {

                                        System.out.println("Delete");
                                        membersData = membersTableView.getSelectionModel().getSelectedItem();

                                        System.out.println(membersData.getMemberUserName());
                                        DatabaseConnection databaseConnection = new DatabaseConnection();
                                        Connection connection = databaseConnection.getConnection();
                                        String sql = "DELETE FROM members WHERE userID = ? AND userName = ?";
                                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                                        preparedStatement.setString(1, String.valueOf(userId));
                                        preparedStatement.setString(2, membersData.getMemberUserName());

                                        System.out.println(membersData.getMemberId());
                                        membersTableView.getItems().remove(membersData);
                                        preparedStatement.execute();
                                        preparedStatement.close();

                                       reloadData();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                setGraphic(delete);
                            }
                            membersTableView.setEditable(false);
                        }
                    };
                    return cell;
                };
        memeberEdit.setCellFactory(cellFoctory);

    }

        @FXML
        public void reloadData() {
            membersTableView.getItems().clear();
            int membersCount = 0;
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, userName, email, dob FROM members WHERE userID = ? AND userName != ?");
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, excludename);
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



    private void getMembersTableData(int id, String excludedUsername)
    {
        membersTableView.getItems().clear();
        int membersCount = 0;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userName, email, dob FROM members WHERE userID = ? AND userName != ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, excludedUsername);
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
    @FXML
    public void homeButtonOnAction(ActionEvent e) throws Exception {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();

    }


    public void setUser(int id, String excludename) {
        this.userId = id;
        this.excludename = excludename;
        getMembersTableData(userId, excludename);
    }

    public void refreshData() {
        getMembersTableData(userId, excludename);
    }

    public void reload(MouseEvent event) {
       // reloadData();
    }
}