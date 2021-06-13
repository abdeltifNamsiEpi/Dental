package com.example.bus.dental.models;

import com.google.gson.annotations.SerializedName;

public class Lesson {
    @SerializedName("id")
    private int id;
    @SerializedName("subjectId")
    private int idSubject;
    @SerializedName("lessonTitle")
    private String lessonTitle;

    public Lesson(int id, int idSubject, String lessonTitle) {
        this.id = id;
        this.idSubject = idSubject;
        this.lessonTitle = lessonTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }


}
