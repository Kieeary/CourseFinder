package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// courseInfo테이블만 참조해서 얻어오는 json
public class SelectCourseList {
    @SerializedName("results")
    ArrayList<CourseListVo> courseListVos = null;

    public ArrayList<CourseListVo> getCourseLists() {
        return courseListVos;
    }

    public void setCourseLists(ArrayList<CourseListVo> courseListVos) {
        this.courseListVos = courseListVos;
    }
}
