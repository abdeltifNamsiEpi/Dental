package com.example.bus.dental.models;

import com.google.firebase.database.ServerValue;

public class Comment {
    private String idComment,comment,uname,ulastname,uid;
    private Object timestamp;


    public Comment() {
    }

    public Comment(String idComment,String comment, String uname,String ulastname, String uid) {
        this.idComment=idComment;
        this.comment = comment;
        this.uname = uname;
        this.ulastname=ulastname;
        this.uid = uid;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    
    public String getUlastname() {
        return ulastname;
    }

    public void setUlastname(String ulastname) {
        this.ulastname = ulastname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
