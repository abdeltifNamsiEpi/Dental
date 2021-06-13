package com.example.bus.dental.models;

public class Title {
    private int id;
    private int idLesson;
    private String titleContent;
    private int orderValue;

    public Title(int id, int idLesson, String titleContent, int orderValue) {
        this.id = id;
        this.idLesson = idLesson;
        this.titleContent = titleContent;
        this.orderValue = orderValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(int idLesson) {
        this.idLesson = idLesson;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }
}
