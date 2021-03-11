package com.alexis.matatu.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostsModel {

    private String messageText;
    private String messageSender;
    private String messageTime;

    public PostsModel(String messageText, String messageSender) {
        this.messageText = messageText;
        this.messageSender = messageSender;

        // Initialize to current time
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        messageTime = formatter.format(date);

    }

    public PostsModel() {

    }

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
