package com.example.demo.service;

import com.example.demo.domain.ChatUser;

import java.util.List;
import java.math.BigInteger;

public interface UserService {

    ChatUser Login(String username, String password);
    ChatUser GetUserById(BigInteger id);
    ChatUser GetUserByName(String Name);
    Boolean addUser(ChatUser chatUser);
    String getUserPassword(String username);
    List<ChatUser> findAllUser();
}
