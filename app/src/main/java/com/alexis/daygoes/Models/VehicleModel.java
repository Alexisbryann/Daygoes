package com.alexis.daygoes.Models;

public class VehicleModel {

    private String Image1;
    private String Name;
    private String Sacco;
    private String Route;
    private String Capacity;
    private String Plate;
    private  int Rating;

    public VehicleModel() {
    }
    public VehicleModel(String image, String name, String sacco, String route, String capacity, String plate, int rating) {
        Image1 = image;
        Name = name;
        Sacco = sacco;
        Route = route;
        Capacity = capacity;
        Plate = plate;
        Rating = rating;

    }

    public String getSacco() {
        return Sacco;
    }

    public void setSacco(String sacco) {
        Sacco = sacco;
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

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}
