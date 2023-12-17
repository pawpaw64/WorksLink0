package com.example.workslink;

import javafx.beans.property.SimpleStringProperty;

public class UserRequestData {
    private int requestId;
    private int senderUserId;
    private int receiverUserId;
    private String status;
    private SimpleStringProperty name;

    public SimpleStringProperty nameProperty() {
        return name;
    }

    // Constructor for creating UserRequestData with senderUsername, senderUserId, and status
    public UserRequestData(String senderUsername, int senderUserId, String status, int requestID, int receieverID) {
        this.name=new SimpleStringProperty(senderUsername);
        this.status = status;
        this.senderUserId = senderUserId;
        this.requestId=requestID;
        this.receiverUserId=receieverID;
    }

    // Constructor for creating UserRequestData with requestId, senderUserId, receiverUserId, and status

    public int getRequestId() {
        return requestId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public String getStatus() {
        return status;
    }
}
