package com.example.bus.dental.models;

public class Paragraph {
    private int id;
    private int idLesson;
    private String paragraphContent;
    private int orderValue;

    public Paragraph(int id, int idLesson, String paragraphContent, int orderValue) {
        this.id = id;
        this.idLesson = idLesson;
        this.paragraphContent = paragraphContent;
        this.orderValue = orderValue;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
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

    public String getParagraphContent() {
        return paragraphContent;
    }

    public void setParagraphContent(String paragraphContent) {
        this.paragraphContent = paragraphContent;
    }
}
