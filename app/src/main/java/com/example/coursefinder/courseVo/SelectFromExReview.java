package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectFromExReview {
    @SerializedName("results")
    ArrayList<ExerciseReviewDetail> execiseReview = null;

    public ArrayList<ExerciseReviewDetail> getExerciseReview() {
        return execiseReview;
    }

    public void setExeciseReview(ArrayList<ExerciseReviewDetail> execiseReview) {
        this.execiseReview = execiseReview;
    }
}
