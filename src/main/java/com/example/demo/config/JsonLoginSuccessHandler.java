package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.JwtUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private JwtUserService jwtUserService;
	
	public JsonLoginSuccessHandler(JwtUserService jwtUserService) {
		this.jwtUserService = jwtUserService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String token=null;
		token=request.getHeader("Authorization");
		if (token==null){
		String gettoken = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
		response.setHeader("Authorization", gettoken);
		}
		else
			response.setHeader("Authorization", token);
		response.setCharacterEncoding("utf-8");
	}
	
}
