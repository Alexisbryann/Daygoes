package com.alexis.daygoes.Models;

public class NewVehiclesModel {

    private String image1;
    private String name;

    public NewVehiclesModel() {
    }

    public NewVehiclesModel(String img, String name) {
        this.image1 = img;
        this.name = name;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
