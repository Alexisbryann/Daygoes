package com.alexis.matatu.Models;

import java.util.ArrayList;

public class FavouriteVehicleModel {
    private String Image1;
    private String Name;
    private String Sacco;
    private String Route;
    private String Capacity;
    private String Plate;
    private Long Rating;

    public FavouriteVehicleModel(String image1, String name, String sacco, String route, String capacity, String plate, Long rating) {
        Image1 = image1;
        Name = name;
        Sacco = sacco;
        Route = route;
        Capacity = capacity;
        Plate = plate;
        Rating = rating;
    }

    public FavouriteVehicleModel() {
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

    public String getSacco() {
        return Sacco;
    }

    public void setSacco(String sacco) {
        Sacco = sacco;
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

    public Long getRating() {
        return Rating;
    }

    public void setRating(Long rating) {
        Rating = rating;
    }
}

