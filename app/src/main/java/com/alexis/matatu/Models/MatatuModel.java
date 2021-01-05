package com.alexis.matatu.Models;

public class MatatuModel {

    public int Image;
    public String Name;
    public String Route;
    public String Capacity;
    public String Plate;
    public int Ratings;

    public MatatuModel(int image, String name, String route, String capacity, String plate, int ratings) {
        Image = image;
        Name = name;
        Route = route;
        Capacity = capacity;
        Plate = plate;
        Ratings = ratings;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
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
