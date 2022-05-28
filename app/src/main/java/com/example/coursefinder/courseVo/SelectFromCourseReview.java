package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectFromCourseReview {
    @SerializedName("results")
    ArrayList<CourseReviewDetail> courseReview = null;

    public ArrayList<CourseReviewDetail> getCourseReview() {
        return courseReview;
    }

    public void setCourseReview(ArrayList<CourseReviewDetail> courseReview) {
        this.courseReview = courseReview;
    }
}
