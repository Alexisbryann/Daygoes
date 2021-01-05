package com.alexis.matatu.Models;

public class RoutesModel {
    String From;
    String Destination;


    public RoutesModel(String from, String destination) {
        From = from;
        Destination = destination;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }
}
