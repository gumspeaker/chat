package com.example.demo;

import com.example.demo.dao.MessageDao;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    MessageDao messageDao;
    @Autowired
    UserDao userDao;
    @Test
    void contextLoads() {
//        Message message=new Message(2,"nihao",new Date(),"小红",false,"normal");
//        System.out.println( messageDao.GetNewMessage(13,100));
        //userService.addUser(new User("sun","98989"));
       // System.out.println( messageDao.GetUserMessage("小红"));
        System.out.println(userDao.FindUserByUserName("问问3"));
    }

}
