package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

// courseInfo테이블만 참조해서 얻어오는 json
public class CourseListVo  implements Serializable {
    @SerializedName("ci_idx")
    private int ci_idx;
    @SerializedName("ci_name")
    private String ci_name;
    @SerializedName("ci_info")
    private String ci_info;
    @SerializedName("ci_price")
    private int ci_price;
    @SerializedName("ci_img")
    private String ci_img;
    @SerializedName("ci_grade")
    private float ci_grade;
    @SerializedName("mi_id")
    private String mi_id;
    @SerializedName("ci_isact")
    private String ci_isact;
    @SerializedName("ci_cata")
    private String ci_cata;

    public int getCi_idx() {
        return ci_idx;
    }

    public void setCi_idx(int ci_idx) {
        this.ci_idx = ci_idx;
    }

    public String getCi_name() {
        return ci_name;
    }

    public void setCi_name(String ci_name) {
        this.ci_name = ci_name;
    }

    public String getCi_info() {
        return ci_info;
    }

    public void setCi_info(String ci_info) {
        this.ci_info = ci_info;
    }

    public int getCi_price() {
        return ci_price;
    }

    public void setCi_price(int ci_price) {
        this.ci_price = ci_price;
    }

    public String getCi_img() {
        return ci_img;
    }

    public void setCi_img(String ci_img) {
        this.ci_img = ci_img;
    }

    public String getCi_grade() {
        return String.valueOf(ci_grade);
    }

    public void setCi_grade(float ci_grade) {
        this.ci_grade = ci_grade;
    }

    public String getMi_id() {
        return mi_id;
    }

    public void setMi_id(String mi_id) {
        this.mi_id = mi_id;
    }

    public String getCi_isact() { return ci_isact; }

    public void setCi_isact(String ci_isact) { this.ci_isact = ci_isact; }

    public String getCi_cata() { return ci_cata; }

    public void setCi_cata(String ci_cata) { this.ci_cata = ci_cata; }
}
