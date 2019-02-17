package com.example.android.finalproject.model;

import com.google.gson.annotations.SerializedName;

public class ShopItems {
    @SerializedName("date_created")
    private String mDateCreated;

    @SerializedName("rating_count")
    private int mRatingCount;

    @SerializedName("id")
    private int mID;

    public int getRatingCount() {
        return mRatingCount;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public ShopItems(int rating, String caption, int id) {
        mRatingCount = rating;
        mDateCreated = caption;
        mID = id;
    }
}
