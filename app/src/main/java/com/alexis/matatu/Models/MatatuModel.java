package com.alexis.matatu.Models;

public class MatatuModel {

    private String Image;
    private String Name;
    private String Sacco;
    private String Route;
    private String Capacity;
    private String Plate;
    private int Ratings;

    public MatatuModel() {
    }
    public MatatuModel(String image, String name, String sacco, String route, String capacity, String plate, int ratings) {
        Image = image;
        Name = name;
        Sacco = sacco;
        Route = route;
        Capacity = capacity;
        Plate = plate;
        Ratings = ratings;
    }

    public String getSacco() {
        return Sacco;
    }

    public void setSacco(String sacco) {
        Sacco = sacco;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public int getRatings() {
        return Ratings;
    }

    public void setRatings(int ratings) {
        Ratings = ratings;
    }
}
