package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.ChatUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtil {


    public static String getToken(UserDetails user) {
//        String token="";
//        token= JWT.create().withAudience(chatUser.getUsername())
//                .sign(Algorithm.HMAC256(chatUser.getPassword()));
//        return token;
        Date date = new Date(System.currentTimeMillis()+3600*1000*12*7);
        Algorithm algorithm = Algorithm.HMAC256("123456ef");
        return JWT.create()
                .withSubject(user.getUsername())
                .withAudience(user.getUsername())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public static String getUserNameFromJwt(String jwt){
        return JWT.decode(jwt).getAudience().get(0);
    }
    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static String verify(String token){
        try {
//            Algorithm algorithm = Algorithm.HMAC256(chatUser.getPassword());
//            JWTVerifier verifier = JWT.require(algorithm).build();
//            DecodedJWT jwt = verifier.verify(token);
//            String username = jwt.getClaim("username").toString();
//            return username;
            Algorithm algorithm=Algorithm.HMAC256("123456ef");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String username=null;
            username=jwt.getClaims().get("username").toString();
           return username;
        } catch (Exception e){
            throw new RuntimeException("401");
        }
    }

}
