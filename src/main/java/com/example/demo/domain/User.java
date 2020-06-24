package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String photo;
    private String userPhone;
    private String role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
