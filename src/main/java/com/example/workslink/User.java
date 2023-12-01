package com.example.workslink;

public class User {
    private String email;
    private String userName;
    private String dob;
    private String bio;

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public User(String email, String userName, String dob) {
        this.email = email;
        this.userName = userName;
        this.dob = dob;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public  User(){

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



