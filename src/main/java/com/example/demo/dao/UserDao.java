package com.example.demo.dao;

import com.example.demo.domain.User;
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
    List<User> findAll();

    /**
     * 通过用户id查询用户
     *
     * @param id 用户id
     * @return
     */
    User findUserById(BigInteger id);

    List<User> FindAll();

    /**
     *  通过账号密码来登录
     * @param userName
     * @param passWord
     * @return
     */
    User Login(String userName ,String passWord);

    /**
     *  通过账号密码来登录
     * @param userName
     *
     * @return
     */
    User FindUserByUserName (String userName);
    boolean addUser(User user);
    Boolean isExistedUser(String Username);
    String getUserPassword(String username);
}
