package com.alexis.matatu.Models;

public class User {
    public   String username;
    public String displayName;
    public   String email;

    public User() {
    }

    public User(String username, String email, String displayName) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
    }
}
