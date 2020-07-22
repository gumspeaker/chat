package com.example.demo;

import com.example.demo.common.commonData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        System.out.println(commonData.ROOT_PATH);
    }
}
