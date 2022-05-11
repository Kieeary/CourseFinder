package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

public class ExerciseReviewDetail {
    @SerializedName("wcr_idx")
    private int wcr_idx;
    @SerializedName("wcr_id")
    private String wcr_id;
    @SerializedName("wcr_name")
    private String wcr_name;
    @SerializedName("mi_id")
    private String mi_id;
    @SerializedName("wci_idx")
    private String wci_idx;
    @SerializedName("wcr_content")
    private String wcr_content;
    @SerializedName("wcr_date")
    private String wcr_date;
    @SerializedName("wcr_isview")
    private String wcr_isview;
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

    public String getWcr_id() { return wcr_id; }

    public void setWcr_id(String wcr_id) {
        this.wcr_id = wcr_id;
    }

    public String getWcr_name() { return wcr_name; }

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
        return wci_idx;
    }

    public void setWi_idx(String wi_idx) {
        this.wci_idx = wi_idx;
    }

    public String getWcr_content() { return wcr_content; }

    public void setWcr_content(String wcr_content) {
        this.wcr_content = wcr_content;
    }

    public String getWcr_date() {
        return wcr_date;
    }

    public void setWcr_date(String wcr_date) {
        this.wcr_date = wcr_date;
    }

    public String getWcr_isview() { return wcr_isview; }

    public void setWcr_isview(String wcr_isview) {
        this.wcr_isview = wcr_isview;
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
