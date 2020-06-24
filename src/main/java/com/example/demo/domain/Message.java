package com.example.demo.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Message {
    private int id;
    private String body;
    private Date date;
    private String owner;
    private boolean deleted;
    private String type;

    public Message(int id, String body, Date date, String owner, boolean deleted, String type) {
        this.id = id;
        this.body = body;
        this.date = date;
        this.owner = owner;
        this.deleted = deleted;
        this.type = type;
    }
    public Message(String body, Date date, String owner, boolean deleted, String type){
        this.body = body;
        this.date = date;
        this.owner = owner;
        this.deleted = deleted;
        this.type = type;
    }
}
