package com.alexis.daygoes.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostsModel2 {

    private String messageText;
    private String messageSender;
    private String messageTime;
    private String url;

    public PostsModel2(String messageText, String messageSender, String url) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.url = url;

        // Initialize to current time
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        messageTime = formatter.format(date);

    }

    public PostsModel2() {

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
