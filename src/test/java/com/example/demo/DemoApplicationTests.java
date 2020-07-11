package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.commonData;
import com.example.demo.dao.MessageDao;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
//    @Autowired
//    UserService userService;
//    @Autowired
//    MessageDao messageDao;
//    @Autowired
//    UserDao userDao;
    @Test
    void contextLoads() {
        commonData commonData = new commonData();
        System.out.println(commonData.RootPath);
    }
}
