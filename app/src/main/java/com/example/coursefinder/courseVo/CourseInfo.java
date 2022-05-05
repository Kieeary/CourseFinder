package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

// view를 참조하여 코스 세부 정보를 받아오는 json
public class CourseInfo implements Serializable {
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
    @SerializedName("cp_order")
    private int cp_order;
    @SerializedName("cp_name")
    private String cp_name;
    @SerializedName("cp_la")
    private double cp_la;
    @SerializedName("cp_lt")
    private double cp_lt;
    @SerializedName("cp_addr")
    private String cp_addr;
    @SerializedName("cp_cata")
    private String cp_cata;
    @SerializedName("mi_id")
    private String mi_id;
    @SerializedName("cp_img")
    private String cp_img;

    public String getCp_img() {
        return cp_img;
    }

    public void setCp_img(String cp_img) {
        this.cp_img = cp_img;
    }

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

    public float getCi_grade() {
        return ci_grade;
    }

    public void setCi_grade(float ci_grade) {
        this.ci_grade = ci_grade;
    }

    public int getCp_order() {
        return cp_order;
    }

    public void setCp_order(int cp_order) {
        this.cp_order = cp_order;
    }

    public String getCp_name() {
        return cp_name;
    }

    public void setCp_name(String cp_name) {
        this.cp_name = cp_name;
    }

    public double getCp_la() {
        return cp_la;
    }

    public void setCp_la(double cp_la) {
        this.cp_la = cp_la;
    }

    public double getCp_lt() {
        return cp_lt;
    }

    public void setCp_lt(double cp_lt) {
        this.cp_lt = cp_lt;
    }

    public String getCp_addr() {
        return cp_addr;
    }

    public void setCp_addr(String cp_addr) {
        this.cp_addr = cp_addr;
    }

    public String getCp_cata() {
        return cp_cata;
    }

    public void setCp_cata(String cp_cata) {
        this.cp_cata = cp_cata;
    }

    public String getMi_id() {
        return mi_id;
    }

    public void setMi_id(String mi_id) {
        this.mi_id = mi_id;
    }
}
