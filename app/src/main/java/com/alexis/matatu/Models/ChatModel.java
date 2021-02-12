package com.alexis.matatu.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ChatModel {

    private String chatGroup;
    private String messageText;
    private String messageSender;
    private String messageTime;

    public ChatModel(String messageText,String messageSender) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.chatGroup = chatGroup;

        // Initialize to current time
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        messageTime = formatter.format(date);

    }

    public ChatModel() {

    }

    public String getChatGroup() {return chatGroup;}

    public void setChatGroup(String chatGroup) {this.chatGroup = chatGroup;}

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

}
