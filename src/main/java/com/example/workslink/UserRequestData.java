package com.example.workslink;

public class UserRequestData {   private int requestId;
    private int senderUserId;
    private int receiverUserId;
    private String status;
    private String name;

    public UserRequestData(String senderUsername,int senderUserId ,String status) {
        this.name=senderUsername;
        this.status=status;
        this.senderUserId=senderUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRequestData(int requestId, int senderUserId, int receiverUserId, String status) {
        this.requestId = requestId;
        this.senderUserId = senderUserId;//sender
        this.receiverUserId = receiverUserId;
        this.status = status;
    }

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
