package com.alexis.matatu.Models;

import android.media.Image;

import com.google.firebase.auth.FirebaseAuth;

public class IndividualVehicleModel {

    private int Likes;
    private int Favourites;
    private Image Like;
    private Image Favourite;
    private float RatingBar;
    private String mName;

    private FirebaseAuth mAuth;
    private String mUserId;
    private float mRating;

    public IndividualVehicleModel() {
    }

    public IndividualVehicleModel(int likes, int favourites, Image like, Image favourite, float ratingBar, String name) {
        Likes = likes;
        Favourites = favourites;
        Like = like;
        Favourite = favourite;
        RatingBar = ratingBar;
    }
    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public int getFavourites() {
        return Favourites;
    }

    public void setFavourites(int favourites) {
        Favourites = favourites;
    }

    public Image getLike() {
        return Like;
    }

    public void setLike(Image like) {
        Like = like;
    }

    public Image getFavourite() {
        return Favourite;
    }

    public void setFavourite(Image favourite) {
        Favourite = favourite;
    }

    public float getRatingBar() {
        return RatingBar;
    }

    public void setRatingBar(float ratingBar) {
        RatingBar = ratingBar;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
