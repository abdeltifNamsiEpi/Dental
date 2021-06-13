package com.example.bus.dental.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Subject {
    @SerializedName("id")
    private int id;
    @SerializedName("subjectName")
    private String subjectName;

    @SerializedName("lessons")
    private ArrayList<Lesson> lessons;

    public Subject(int id, String subjectName, ArrayList<Lesson> lessons) {
        this.id = id;
        this.subjectName = subjectName;
        this.lessons = lessons;
    }

    public Subject(int id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
