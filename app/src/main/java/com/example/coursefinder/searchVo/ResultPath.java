package com.example.coursefinder.searchVo;

import com.google.gson.annotations.SerializedName;

public class ResultPath {
    @SerializedName("route")
    Route trackOption;
    @SerializedName("message")
    String message;
    @SerializedName("code")
    int code;

    public Route getTrackOption() {
        return trackOption;
    }

    public void setTrackOption(Route trackOption) {
        this.trackOption = trackOption;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
