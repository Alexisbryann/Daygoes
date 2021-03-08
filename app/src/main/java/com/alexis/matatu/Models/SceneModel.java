package com.alexis.matatu.Models;

public class SceneModel {
    private String chatGroup;

    public SceneModel(String name) {
        this.chatGroup = name;
    }

    public SceneModel() {

    }

    public String getChatGroup() {
        return chatGroup;
    }

    public void setChatGroup(String chatGroup) {
        this.chatGroup = chatGroup;
    }
}
