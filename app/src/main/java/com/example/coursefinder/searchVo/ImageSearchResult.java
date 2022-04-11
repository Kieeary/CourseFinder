package com.example.coursefinder.searchVo;

import com.example.coursefinder.searchVo.ImageList;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageSearchResult {
    @SerializedName("items")
    ArrayList<ImageList> imgResult = null;

    public ArrayList<ImageList> getImgResult() {
        return imgResult;
    }

    public void setImgResult(ArrayList<ImageList> imgResult) {
        this.imgResult = imgResult;
    }

}
