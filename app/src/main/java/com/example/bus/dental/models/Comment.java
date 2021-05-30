package com.example.bus.dental.models;

import com.google.firebase.database.ServerValue;

public class Comment {
    private String comment,uname,uid;
    private Object timestamp;


    public Comment() {
    }

    public Comment(String comment, String uname, String uid) {

        this.comment = comment;
        this.uname = uname;
        this.uid = uid;
        this.timestamp = ServerValue.TIMESTAMP;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
