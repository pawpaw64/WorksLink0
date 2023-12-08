package com.example.workslink;

import com.example.workslink.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    Button button;
    @FXML
    TextField inputfield;
    BufferedReader reader;
    BufferedWriter writer;
    @FXML
    TextArea showArea;
    @FXML
    Button minimize;
    public User userProfile;
    boolean isConnected = false;
    String name;

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;

    }


    @FXML
    void buttonPrassed() {
        if (!isConnected) {
            System.out.printf(userProfile.getUserName());

            String inputName = userProfile.getUserName();
            inputfield.clear();
            if(inputName==null || inputName.length() == 0){
                showArea.appendText("\n");//show
                return;
            }
            try {
                Socket socket = new Socket("localhost", 6600);

                OutputStreamWriter o = new OutputStreamWriter(socket.getOutputStream());
                writer = new BufferedWriter(o);

                writer.write(inputName+"\n");//show msg
                writer.flush();


                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isr);

                 Thread serverListener = new Thread(){
                     @Override
                     public void run(){
                         while (true){
                             try {
                                 String data = reader.readLine()+"\n";
                                 showArea.appendText(data);
                                 storeMessegeInFile(data);
                             }catch (SocketException ee){
                                 showArea.appendText("Connection lost"+"\n");
                                 break;
                             }
                             catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                 };
                    serverListener.start();

                showArea.appendText("");
                button.setText("Send");
                inputfield.setPromptText("Write your massage...");
                isConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                String msg = inputfield.getText();
                inputfield.clear();

                System.out.println(msg);
                if(msg==null || msg.length() == 0){
                    showArea.appendText("");
                    return;
                }
                writer.write(msg+"\n");
                writer.flush();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void storeMessegeInFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("F:\\AOOP Project\\AOOP_Project\\WorksLink0\\src\\main\\java\\com\\example\\workslink\\previousMessage.txt", true))) {
            System.out.println("ihhsuikdfuerg");
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(userProfile!=null){
            name = userProfile.getUserName();
            System.out.printf(name);
        }
        else {
            //System.out.printf("Null name");
        }
    }

    private Button homeButton;

    public void homeButtonOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) homeButton.getScene().getWindow();

        stage.close();
    }
}