package com.alexis.matatu.Models;

public class User {
    public String username;
//    public String displayName;
//    public String email;
    public String phone;

    public User() {
    }

    public User(String username, String phone) {
        this.username = username;
//        this.email = email;
//        this.displayName = displayName;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public void setDisplayName(String displayName) {
//        this.displayName = displayName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
