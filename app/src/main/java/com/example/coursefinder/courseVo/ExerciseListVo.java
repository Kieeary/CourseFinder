package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExerciseListVo implements Serializable {
    @SerializedName("wi_idx")
    private String wi_idx;
    @SerializedName("wi_name")
    private String wi_name;
    @SerializedName("wi_info")
    private String wi_info;
    @SerializedName("wi_grade")
    private String wi_grade;
    @SerializedName("mi_id")
    private String mi_id;
    @SerializedName("wi_time")
    private String wi_time;
    @SerializedName("wi_isact")
    private String wi_isact;


    public String getWi_idx() {
        return wi_idx;
    }

    public void setWi_idx(String wi_idx) {
        this.wi_idx = wi_idx;
    }

    public String getWi_name() {
        return wi_name;
    }

    public void setWi_name(String wi_name) {
        this.wi_name = wi_name;
    }

    public String getWi_info() {
        return wi_info;
    }

    public void setWi_info(String wi_info) {
        this.wi_info = wi_info;
    }

    public String getWi_grade() {
        return wi_grade;
    }

    public void setWi_grade(String wi_grade) {
        this.wi_grade = wi_grade;
    }

    public String getMi_id() {
        return mi_id;
    }

    public void setMi_id(String mi_id) {
        this.mi_id = mi_id;
    }

    public String getWi_time() {
        return wi_time;
    }

    public void setWi_time(String wi_time) {
        this.wi_time = wi_time;
    }

    public String getWi_isact() {
        return wi_isact;
    }

    public void setWi_isact(String wi_isact) {
        this.wi_isact = wi_isact;
    }
}
