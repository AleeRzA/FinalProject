package com.example.android.finalproject.model;


import com.google.gson.annotations.SerializedName;

public class Image {


    @SerializedName("src")
    private String imgSrc;

    public Image(String path) {
        this.imgSrc = path;
    }

    public String getImgSrc() {
        return imgSrc;
    }

}

