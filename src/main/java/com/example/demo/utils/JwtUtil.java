package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.ChatUser;

import java.util.Date;

public class JwtUtil {

    public static String getToken(ChatUser user) {
        String token="";
        token= JWT.create().withAudience(user.getUsername())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }


    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static String verify(String token,ChatUser user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String username = jwt.getClaim("username").toString();
            return username;
        } catch (Exception e){
            throw new RuntimeException("401");
        }
    }
    public static String getUser(String token){
        return JWT.decode(token).getAudience().
    }
}
