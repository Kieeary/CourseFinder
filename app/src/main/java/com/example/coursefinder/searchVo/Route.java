package com.example.coursefinder.searchVo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {
    @SerializedName("traoptimal")
    private List<Traoptimal> traoptimal;

    public List<Traoptimal> getTraoptimal() {
        return traoptimal;
    }

    public void setTraoptimal(List<Traoptimal> traoptimal) {
        this.traoptimal = traoptimal;
    }
}
