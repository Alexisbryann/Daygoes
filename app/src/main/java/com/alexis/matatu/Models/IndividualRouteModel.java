package com.alexis.matatu.Models;

public class IndividualRouteModel {
    private String Image1;
    private String Name;
    private String Route;
    private String Capacity;
    private String Plate;
    private  float Rating;

    public IndividualRouteModel() {
    }

    public IndividualRouteModel(String image1, String name, String route, String capacity, String plate, float rating) {
        Image1 = image1;
        Name = name;
        Route = route;
        Capacity = capacity;
        Plate = plate;
        Rating = rating;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getPlate() {
        return Plate;
    }

    public void setPlate(String plate) {
        Plate = plate;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }
}
