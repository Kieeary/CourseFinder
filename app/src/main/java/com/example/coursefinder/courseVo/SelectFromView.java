package com.example.coursefinder.courseVo;

import com.example.coursefinder.searchVo.PlaceList;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// view를 참조하여 코스 세부 정보를 받아오는 json
public class SelectFromView {
    @SerializedName("results")
    ArrayList<CourseInfo> coursePlaceInfo = null;

    public ArrayList<CourseInfo> getCourseLists() {
        return coursePlaceInfo;
    }

    public void setCourseLists(ArrayList<CourseInfo> coursePlaceInfo) {
        this.coursePlaceInfo = coursePlaceInfo;
    }
}
