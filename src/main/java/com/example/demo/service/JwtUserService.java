package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.domain.ChatUser;
import com.example.demo.service.Impl.UserServiceImpl;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


public class JwtUserService implements UserDetailsService{
	
	private PasswordEncoder passwordEncoder;
	private Logger logger=LoggerFactory.getLogger(JwtUserService.class);
	@Autowired
	UserService userService;

	public JwtUserService() {
		this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();  //默认使用 bcrypt， strength=10 
	}

	public UserDetails getUserLoginInfo(String username) {
		String salt = "123456ef";
    	/**
    	 * @todo 从数据库或者缓存中取出jwt token生成时用的salt
    	 * salt = redisTemplate.opsForValue().get("token:"+username);
    	 */   	
    	UserDetails user = loadUserByUsername(username);
    	//将salt放到password字段返回
    	return User.builder().username(user.getUsername()).password(salt).authorities(user.getAuthorities()).build();
	}
	
	public String saveUserLoginInfo(UserDetails user) {
		String salt = "123456ef"; //BCrypt.gensalt();  正式开发时可以调用该方法实时生成加密的salt
		/**
    	 * @todo 将salt保存到数据库或者缓存中
    	 * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
    	 */   	
		Algorithm algorithm = Algorithm.HMAC256(salt);
		Date date = new Date(System.currentTimeMillis()+3600*1000*12*7);  //设置7天后过期
        return JWT.create()
        		.withSubject(user.getUsername())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ChatUser chatUser =userService.GetUserByName(username);
		if (chatUser==null)
			throw new UsernameNotFoundException("用户为空");
		return User.builder().username(chatUser.getUsername()).password(passwordEncoder.encode(chatUser.getPassword())).roles("USER").build();
	}

	public Boolean createUser(String username, String password) {

		boolean flag=userService.addUser(new ChatUser(username,password));
		/**
		 * @todo 保存用户名和加密后密码到数据库
		 */
		return flag;
	}
	
	public void deleteUserLoginInfo(String username) {
		/**
		 * @todo 清除数据库或者缓存中登录salt
		 */
	}
}
