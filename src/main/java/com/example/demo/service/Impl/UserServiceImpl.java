package com.example.demo.service.Impl;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;
    @Override
    public User Login(String username, String password) {
        User user=userdao.Login(username,password);
        return user;
    }

    @Override
    public User GetUserById(BigInteger id) {
        return userdao.findUserById(id);
    }

    @Override
    public User GetUserByName(String Name) {
        return userdao.FindUserByUserName(Name);
    }

    @Override
    public Boolean addUser(User user) {
        String userName=user.getUsername();
        if(userdao.isExistedUser(userName))
            return false;
        return userdao.addUser(user);
    }

    @Override
    public String getUserPassword(String username) {
            return userdao.getUserPassword(username);
    }
}
