package com.example.demo.utils;

import com.example.demo.domain.ChatUser;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Component("cookieUtil")
public class CookieUtil {

    private static Logger logger = LogManager.getLogger(CookieUtil.class);
    ///private static CookieUtil cookieUtil;

    @PostConstruct
    public void init() {
        //cookieUtil = this;
        //cookieUtil.userService = this.userService;
    }

    public static void setUserIDCookie(HttpServletResponse response, String userName)
            throws UnsupportedEncodingException {
        Cookie cookie = new Cookie("userName", URLEncoder.encode(userName, "UTF-8"));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 24 * 365);
        response.addCookie(cookie);
    }

    /**
     * @param request
     * @return String
     * @Title: readUserIDFromCookie
     * @Description: Read userID from cookies.
     */
    public static String readUserIDFromCookie(HttpServletRequest request,UserService userService) {
        Cookie[] cookies = request.getCookies();
        String userName = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userName")) {
                    userName = cookie.getValue();

                    if (userName != null) {
                        try {
                            userName = URLDecoder.decode(userName, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            logger.error("Error in getUserIDFromCookies: ", e);
                            userName = null;
                        }
                    }
                    if (userName != null) {
                        ChatUser chatUser = userService.GetUserByName(userName);
                        if (chatUser == null) {
                            logger.warn("userName " + userName + " is not existed.");
                            userName = null;
                        }
                    } else {
                        // userID == null
                        logger.warn("userName's value in the cookie map is null.");
                    }
                    break;
                }
            }
        }
        return userName;
    }


}