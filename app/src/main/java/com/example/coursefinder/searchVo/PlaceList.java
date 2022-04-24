package com.example.coursefinder.searchVo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class PlaceList implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("mapx")
    @Expose
    private int mapx;

    @SerializedName("mapy")
    @Expose
    private int mapy;

    @SerializedName("description")
    @Expose
    private String description;

    private String imgLink;

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMapx() {
        return mapx;
    }

    public void setMapx(int mapx) {
        this.mapx = mapx;
    }

    public int getMapy() {
        return mapy;
    }

    public void setMapy(int mapy) {
        this.mapy = mapy;
    }


}
