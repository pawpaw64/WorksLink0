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

    public User(String email, String userName, String dob, int user_id, String bio,byte[] userImg) {

        this.email = email;
        this.userName = userName;
        this.dob = dob;
        this.bio = (bio != null) ? bio : "Default Bio";
        this.userImg = (userImg != null) ? userImg : getDefaultUserImage();
        this.user_id=user_id;
    }
    private byte[] getDefaultUserImage() {
        // Provide a default image as a byte array
        // Example: return Files.readAllBytes(Paths.get("path/to/default/image.jpg"));
        return new byte[0]; // Replace this with the actual default image data
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



