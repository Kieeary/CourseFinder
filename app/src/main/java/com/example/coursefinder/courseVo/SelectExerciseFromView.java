package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectExerciseFromView {
    @SerializedName("results")
    ArrayList<ExerciseInfo> exerciseInfos = null;

    public ArrayList<ExerciseInfo> getExerciseInfos() {
        return exerciseInfos;
    }

    public void setExerciseInfos(ArrayList<ExerciseInfo> exerciseInfos) {
        this.exerciseInfos = exerciseInfos;
    }
}
