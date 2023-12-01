package com.example.workslink;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientController {

    @FXML
    Button button;
    @FXML
    TextField inputfield;
    BufferedReader reader;
    BufferedWriter writer;
    @FXML
    TextArea showArea;
    boolean isConnected = false;

    public ClientController() {

    }
   User user=new User();
    @FXML
    void buttonPrassed() {
        if (!isConnected) {
            String clientName =user.getUserName();
            showArea.setText(clientName);
            System.out.println(clientName);//User name in setText method..
            //Client is not connected with server, let's connect with server...
            String inputName = inputfield.getText();
            inputfield.clear();
            if(inputName==null || inputName.length() == 0){
                showArea.appendText("Enter your name..."+"\n");
                return;
            }
            try {
                Socket socket = new Socket("localhost", 6600);

                OutputStreamWriter o = new OutputStreamWriter(socket.getOutputStream());
                writer = new BufferedWriter(o);

                writer.write(inputName+"\n");
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

                showArea.appendText(inputName+" is Connected.."+"\n");
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
}