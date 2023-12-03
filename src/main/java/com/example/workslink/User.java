package com.example.workslink;

import javafx.scene.text.Text;

public class User {
    private byte[] userImg;
    private String email;
    private String userName;
    private String dob;
    private String bio;
    private int user_id;

    public byte[] getUserImg() {
        return userImg;
    }

    public void setUserImg(byte[] userImg) {
        this.userImg = userImg;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public User(String email, String userName, String dob, int user_id, String bio,byte[] userimg) {
        this.userImg=userimg;
        this.email = email;
        this.userName = userName;
        this.dob = dob;
        this.bio= String.valueOf(bio);
        this.user_id=user_id;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public String getUserName() {
        return userName;
    }
    public String getDob() {
        return dob;
    }

}



