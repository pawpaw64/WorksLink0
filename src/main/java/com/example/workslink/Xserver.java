package com.example.workslink;



import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Xserver extends Application implements Initializable {
    private static HashMap<String, XclientHandler> clients = new HashMap<>();
    private static int clientCounter = 0;

    private User userProfile;
    private static String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        launch(args);
    }

    static void broadcastMessage(String message, XclientHandler sender) {
        logEvent(sender.getClientName() + ": " + message);
        for (XclientHandler client : clients.values()) {
            client.sendMessage(sender.getClientName() + ": " + message);
        }
    }


    private static void sendPreviousMessages(XclientHandler clientHandler) {
        try (Scanner logScanner = new Scanner(new File("SavedMessages.txt"))) {
            while (logScanner.hasNextLine()) {
                String message = logScanner.nextLine();
                clientHandler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void logEvent(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("SavedMessages.txt", true))) {
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
        System.out.println("Xserver   "+userProfile.getUserName());
        name = userProfile.getUserName();

        if(userProfile!=null){
            name = userProfile.getUserName();
            System.out.println(name+"     initialized");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @Override
    public void start(Stage stage) throws Exception {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server started on port 8080...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + name +" newly added");
                XclientHandler clientHandler = new XclientHandler(clientSocket, "Client-" + name);


                clients.put(clientHandler.getClientName(), clientHandler);
                sendPreviousMessages(clientHandler);
                new Thread(clientHandler).start();
                clientCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


