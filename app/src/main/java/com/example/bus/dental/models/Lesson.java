package com.example.bus.dental.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Lesson {
    @SerializedName("id")
    private int id;
    @SerializedName("subjectId")
    private int idSubject;
    @SerializedName("lessonTitle")
    private String lessonTitle;
    @SerializedName("blocs")
    private ArrayList<Bloc> blocs;


    public Lesson(int id, int idSubject, String lessonTitle, ArrayList<Bloc> blocs) {
        this.id = id;
        this.idSubject = idSubject;
        this.lessonTitle = lessonTitle;
        this.blocs = blocs;
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

    public ArrayList<Bloc> getBlocs() {
        return blocs;
    }

    public void setBlocs(ArrayList<Bloc> blocs) {
        this.blocs = blocs;
    }
}
