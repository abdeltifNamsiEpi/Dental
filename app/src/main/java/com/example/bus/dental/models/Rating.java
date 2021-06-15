package com.example.bus.dental.models;

public class Rating {

    private String rate,uName,uLastName,uId,uEmail;

    public Rating(String rate, String uName, String uLastName, String uId,String uEmail) {
        this.rate = rate;
        this.uName = uName;
        this.uLastName = uLastName;
        this.uId = uId;
        this.uEmail=uEmail;
    }

    public Rating() {
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuLastName() {
        return uLastName;
    }

    public void setuLastName(String uLastName) {
        this.uLastName = uLastName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}
