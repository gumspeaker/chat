package com.example.demo.service.Impl;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.ChatUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;
    @Override
    public ChatUser Login(String username, String password) {
        ChatUser chatUser =userdao.Login(username,password);
        return chatUser;
    }

    @Override
    public ChatUser GetUserById(BigInteger id) {
        return userdao.findUserById(id);
    }

    @Override
    public ChatUser GetUserByName(String Name) {
        return userdao.FindUserByUserName(Name);
    }

    @Override
    public Boolean addUser(ChatUser chatUser) {
        String userName= chatUser.getUsername();
        if(userdao.isExistedUser(userName))
            return false;
        return userdao.addUser(chatUser);
    }

    @Override
    public String getUserPassword(String username) {
            return userdao.getUserPassword(username);
    }
}
