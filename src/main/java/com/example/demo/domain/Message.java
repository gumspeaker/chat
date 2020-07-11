package com.example.demo.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class Message implements Serializable {
    private int id;
    private String body;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String owner;
    private boolean deleted;
    private String type;
    public Message(int id, String body, Date date,String owner, boolean deleted, String type) {
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
