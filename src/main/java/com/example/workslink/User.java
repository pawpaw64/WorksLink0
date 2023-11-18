package com.example.workslink;

public class User {
    private String email;
    private String userName;
    private String dob;

    public User(String email, String userName, String dob) {
        this.email = email;
        this.userName = userName;
        this.dob = dob;
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

