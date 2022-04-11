package com.example.coursefinder.searchVo;

import com.google.gson.annotations.SerializedName;

public class ResultDistance {
    @SerializedName("distance")
    private int distance;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
