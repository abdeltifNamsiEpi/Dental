package com.example.bus.dental.models;

public class Bloc {
    private int id;
    private int orderValue;
    private int lessonId;
    private String titleId;
    private String titleContent;
    private String paragraphId;
    private String paragraphContent;
    private String imageId;
    private String url;
    private String legend;

    public Bloc(int id, int orderValue, int lessonId, String titleId, String titleContent, String paragraphId, String paragraphContent, String imageId, String url, String legend) {
        this.id = id;
        this.orderValue = orderValue;
        this.lessonId = lessonId;
        this.titleId = titleId;
        this.titleContent = titleContent;
        this.paragraphId = paragraphId;
        this.paragraphContent = paragraphContent;
        this.imageId = imageId;
        this.url = url;
        this.legend = legend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(String paragraphId) {
        this.paragraphId = paragraphId;
    }

    public String getParagraphContent() {
        return paragraphContent;
    }

    public void setParagraphContent(String paragraphContent) {
        this.paragraphContent = paragraphContent;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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
