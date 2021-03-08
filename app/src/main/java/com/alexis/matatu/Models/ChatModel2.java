package com.alexis.matatu.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatModel2 {

    private String chatGroup;


    public ChatModel2(String chatGroup) {

        this.chatGroup = chatGroup;


    }

    public ChatModel2() {

    }

    public String getChatGroup() {
        return chatGroup;
    }

    public void setChatGroup(String chatGroup) {
        this.chatGroup = chatGroup;
    }


}
