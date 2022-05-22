package com.example.coursefinder.courseVo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectExCourseList {
    @SerializedName("results")
    ArrayList<ExerciseListVo> exerciseLists = null;

    public ArrayList<ExerciseListVo> getExerciseLists() {
        return exerciseLists;
    }

    public void setExerciseLists(ArrayList<ExerciseListVo> exerciseLists) {
        this.exerciseLists = exerciseLists;
    }
}
