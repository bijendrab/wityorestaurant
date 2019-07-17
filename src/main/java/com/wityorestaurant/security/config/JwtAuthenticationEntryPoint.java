package com.wityorestaurant.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.wityorestaurant.security.exception.InvalidLoginResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		InvalidLoginResponse resp = new InvalidLoginResponse();
		String loginJsonResponse = new Gson().toJson(resp);
		response.setContentType("application/json");
		response.setStatus(401);
		response.getWriter().print(loginJsonResponse);
	}

}
