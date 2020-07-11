package com.example.demo.dao;

import com.example.demo.domain.ChatUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Repository
public interface UserDao {
    /**
     * 查询所有用户的信息
     *
     * @return
     */
    List<ChatUser> findAll();

    /**
     * 通过用户id查询用户
     *
     * @param id 用户id
     * @return
     */
    ChatUser findUserById(BigInteger id);

    List<ChatUser> FindAll();

    /**
     *  通过账号密码来登录
     * @param userName
     * @param passWord
     * @return
     */
    ChatUser Login(String userName , String passWord);

    /**
     *  通过账号密码来登录
     * @param userName
     *
     * @return
     */
    ChatUser FindUserByUserName (String userName);
    boolean addUser(ChatUser chatUser);
    Boolean isExistedUser(String Username);
    String getUserPassword(String username);
}
