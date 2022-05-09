package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

public class ExerciseReviewDetail {
    @SerializedName("wcr_idx")
    private int wcr_idx;
    @SerializedName("wcr_name")
    private String wcr_name;
    @SerializedName("mi_id")
    private String mi_id;
    @SerializedName("wi_idx")
    private String wi_idx;
    @SerializedName("wcr_content")
    private String wcr_content;
    @SerializedName("wcr_date")
    private String wcr_date;
    @SerializedName("wcr_grade")
    private int wcr_grade;
    @SerializedName("wcr_img")
    private String wcr_img;

    public int getWcr_idx() {
        return wcr_idx;
    }

    public void setWcr_idx(int wcr_idx) {
        this.wcr_idx = wcr_idx;
    }

    public String getWcr_name() {
        return wcr_name;
    }

    public void setWcr_name(String wcr_name) {
        this.wcr_name = wcr_name;
    }

    public String getMi_id() {
        return mi_id;
    }

    public void setMi_id(String mi_id) {
        this.mi_id = mi_id;
    }

    public String getWi_idx() {
        return wi_idx;
    }

    public void setWi_idx(String wi_idx) {
        this.wi_idx = wi_idx;
    }

    public String getWcr_content() {
        return wcr_content;
    }

    public void setWcr_content(String wcr_content) {
        this.wcr_content = wcr_content;
    }

    public String getWcr_date() {
        return wcr_date;
    }

    public void setWcr_date(String wcr_date) {
        this.wcr_date = wcr_date;
    }

    public int getWcr_grade() {
        return wcr_grade;
    }

    public void setWcr_grade(int wcr_grade) {
        this.wcr_grade = wcr_grade;
    }

    public String getWcr_img() {
        return wcr_img;
    }

    public void setWcr_img(String wcr_img) {
        this.wcr_img = wcr_img;
    }
}
