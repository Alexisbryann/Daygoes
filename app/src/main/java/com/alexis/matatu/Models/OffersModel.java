package com.alexis.matatu.Models;

public class OffersModel {
    String offer;
    String route;
    String vehicle;
    public OffersModel() {
    }

    public OffersModel(String offer, String route, String vehicle) {
        this.offer = offer;
        this.route = route;
        this.vehicle = vehicle;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
