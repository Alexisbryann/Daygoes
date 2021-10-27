package com.alexis.daygoes.Models;

public class ImgModel {

// variables for storing our image and name.
private String name;
private String image1;

public ImgModel() {
    // empty constructor required for firebase.
}

// constructor for our object class.
public ImgModel(String name, String image1) {
    this.name = name;
    this.image1 = image1;
}

// getter and setter methods
public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getImage1() {
    return image1;
}

public void setImage1(String image1) {
    this.image1 = image1;
}
}

