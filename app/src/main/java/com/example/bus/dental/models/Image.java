package com.example.bus.dental.models;

public class Image {
    private int id;
    private int idLesson;
    private int orderValue;
    private String url;
    private String legend;

    public Image(int id, int idLesson, int orderValue, String url, String legend) {
        this.id = id;
        this.idLesson = idLesson;
        this.orderValue = orderValue;
        this.url = url;
        this.legend = legend;
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

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }
}
