package com.example.coursefinder.searchVo;

import com.example.coursefinder.searchVo.PlaceList;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceSearchResult {
    @SerializedName("items")
    ArrayList<PlaceList> placeLists = null;

    public ArrayList<PlaceList> getPlaceLists() {
        return placeLists;
    }

    public void setPlaceLists(ArrayList<PlaceList> placeLists) {
        this.placeLists = placeLists;
    }

}
