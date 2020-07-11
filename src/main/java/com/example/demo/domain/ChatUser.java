package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatUser implements Serializable {
    private int id;
    private String username;
    private String password;
    private String photo;
    private String userPhone;
    private String role;

    public ChatUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
