package com.alexis.matatu.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Chats {

    private String chatGroup;
    private String messageText;
    private String messageUser;
    private String messageTime;

    public Chats(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.chatGroup = chatGroup;

        // Initialize to current time
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM dd hh:mm a", Locale.getDefault());
        messageTime = formatter.format(date);
    }

    public Chats() {

    }

    public String getChatGroup() {return chatGroup;}

    public void setChatGroup(String chatGroup) {this.chatGroup = chatGroup;}

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

}
