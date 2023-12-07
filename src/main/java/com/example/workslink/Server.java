package com.example.workslink;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try {
            System.out.println("Server is waiting for client...");
            ServerSocket serverSocket = new ServerSocket(6600);
            while (true){
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                Thread t = new Thread(client);
                t.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
