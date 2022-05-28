package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

public class CourseReviewDetail {
    @SerializedName("cr_idx")
    String cr_idx;
    @SerializedName("mi_id")
    String mi_id;
    @SerializedName("ci_idx")
    String ci_idx;
    @SerializedName("cr_content")
    String cr_content;
    @SerializedName("cr_date")
    String cr_date;
    @SerializedName("cr_grade")
    String cr_grade;
    @SerializedName("cr_img")
    String cr_img;
    @SerializedName("cr_title")
    String cr_title;

    public String getCr_idx() {
        return cr_idx;
    }

    public void setCr_idx(String cr_idx) {
        this.cr_idx = cr_idx;
    }

    public String getMi_id() {
        return mi_id;
    }

    public void setMi_id(String mi_id) {
        this.mi_id = mi_id;
    }

    public String getCi_idx() {
        return ci_idx;
    }

    public void setCi_idx(String ci_idx) {
        this.ci_idx = ci_idx;
    }

    public String getCr_content() {
        return cr_content;
    }

    public void setCr_content(String cr_content) {
        this.cr_content = cr_content;
    }

    public String getCr_date() {
        return cr_date;
    }

    public void setCr_date(String cr_date) {
        this.cr_date = cr_date;
    }

    public String getCr_grade() {
        return cr_grade;
    }

    public void setCr_grade(String cr_grade) {
        this.cr_grade = cr_grade;
    }

    public String getCr_img() {
        return cr_img;
    }

    public void setCr_img(String cr_img) {
        this.cr_img = cr_img;
    }

    public String getCr_title() {
        return cr_title;
    }

    public void setCr_title(String cr_title) {
        this.cr_title = cr_title;
    }
}
