
package com.example.workslink;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Client implements Runnable {
    String clientName;
    BufferedReader reader;
    BufferedWriter writer;

    final static ArrayList<Client> clients = new ArrayList<>();

    Client(Socket socket){
        try {
            OutputStreamWriter o = new OutputStreamWriter(socket.getOutputStream());
            writer = new BufferedWriter(o);

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isr);

            clientName = reader.readLine();
            clients.add(this);

            sendPreviousMessages();
            System.out.println("Client "+clientName+" connected...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String data = reader.readLine()+"\n";
                data = clientName + ": "+data;
                synchronized (clients){
                    for(Client client:clients){
                        client.writer.write(data);
                        client.writer.flush();
                    }
                }

            } catch (SocketException e){
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPreviousMessages() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("C:\\Users\\USER\\Documents\\GitHub\\WorksLink0\\src\\main\\java\\com\\example\\workslink\\previousMessage.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.write(line + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getChatDatabase() {
        try {
            System.out.println("Getting Data From Database");
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT previousChat FROM chat";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String previousChat = rs.getString("previousChat"+"\n");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
