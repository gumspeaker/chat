package com.example.demo.service;

import com.example.demo.domain.User;

import java.math.BigInteger;

public interface UserService {

    User Login(String username,String password);
    User GetUserById(BigInteger id);
    User GetUserByName(String Name);
    Boolean addUser(User user);
    String getUserPassword(String username);
}
