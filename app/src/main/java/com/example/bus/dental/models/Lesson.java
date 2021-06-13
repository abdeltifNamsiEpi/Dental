package com.example.bus.dental.models;

public class Lesson {
    private int id;
    private int idSubject;
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
