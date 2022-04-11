package com.example.coursefinder.searchVo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Traoptimal {
    @SerializedName("summary")
    private ResultDistance summary;
    @SerializedName("path")
    private List<List<Double>> path;

    public ResultDistance getSummary() {
        return summary;
    }

    public void setSummary(ResultDistance summary) {
        this.summary = summary;
    }

    public List<List<Double>> getPath() {
        return path;
    }

    public void setPath(List<List<Double>> path) {
        this.path = path;
    }
}
