package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

public class ExerciseInfo {
    @SerializedName("wi_idx")
    private String wi_idx;
    @SerializedName("wi_name")
    private String wi_name;
    @SerializedName("wi_info")
    private String wi_info;
    @SerializedName("wi_time")
    private String wi_time;
    @SerializedName("wi_grade")
    private float wi_grade;
    @SerializedName("wcp_order")
    private int wcp_order;
    @SerializedName("mi_id")
    private String mi_id;
    @SerializedName("wcp_at")
    private double wcp_at;
    @SerializedName("wcp_lt")
    private double wcp_lt;

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

    public String getWi_time() {
        return wi_time;
    }

    public void setWi_time(String wi_time) {
        this.wi_time = wi_time;
    }

    public float getWi_grade() {
        return wi_grade;
    }

    public void setWi_grade(float wi_grade) {
        this.wi_grade = wi_grade;
    }

    public int getWcp_order() {
        return wcp_order;
    }

    public void setWcp_order(int wcp_order) {
        this.wcp_order = wcp_order;
    }

    public String getMi_id() {
        return mi_id;
    }

    public void setMi_id(String mi_id) {
        this.mi_id = mi_id;
    }

    public double getWcp_at() {
        return wcp_at;
    }

    public void setWcp_at(double wcp_at) {
        this.wcp_at = wcp_at;
    }

    public double getWcp_lt() {
        return wcp_lt;
    }

    public void setWcp_lt(double wcp_lt) {
        this.wcp_lt = wcp_lt;
    }
}
