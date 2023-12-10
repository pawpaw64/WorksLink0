package com.example.workslink;

import com.example.workslink.Xserver;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

class XclientHandler implements Runnable {
    private Socket clientSocket;
    private String clientName;
    private PrintWriter out;
    private Scanner in;
    public User userProfile;
    String name;

    public XclientHandler(Socket clientSocket, String clientName) {
        this.clientSocket = clientSocket;
        this.clientName = clientName;
        System.out.println(clientName+"     ClientHandler");
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getClientName() {
        return clientName;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (in.hasNext()) {
                    String message = in.nextLine();
                    System.out.println(clientName + " says: " + message);
                    Xserver.broadcastMessage(message, this);
                }
            }
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
        System.out.println("setUserProfile   "+userProfile.getUserName());
        name = userProfile.getUserName();

    }


}
