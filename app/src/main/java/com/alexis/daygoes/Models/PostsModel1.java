package com.alexis.daygoes.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostsModel1 {

    private String messageText;
    private String messageSender;
    private String messageTime;
    private String url;
    private String title;

    public PostsModel1(String messageText, String messageSender, String url, String title) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.url = url;
        this.title = title;


        // Initialize to current time
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        messageTime = formatter.format(date);

    }

    public PostsModel1() {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
