package com.example.coursefinder.MemberVo;

import com.google.gson.annotations.SerializedName;

public class MemberInfo {

    @SerializedName("mi_id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
