package com.alexis.matatu.Models;

import java.util.Date;

public class Chats {

    private String chatGroup;
    private String messageText;
    private String messageUser;
    private long messageTime;

    public Chats(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.chatGroup = chatGroup;

        // Initialize to current time
        messageTime = new Date().getTime();
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

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}
