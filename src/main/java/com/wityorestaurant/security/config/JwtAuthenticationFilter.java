package com.wityorestaurant.security.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wityorestaurant.common.Constant;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.security.service.RestaurantUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider provider;
	
	@Autowired
	private RestaurantUserDetailsService customUserDetailService;
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(Constant.HEADER_TOKEN_KEY);
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constant.TOKEN_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	/* ::::::::::::::::::::::All the request will go through this filter:::::::::::::::::::::: */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			/*
			 * 1. Get token from request
			 * 2. Check if it is valid
			 * 3. Get Id from token
			 * 4. Authenticate the user
			 */
			String token = getJwtFromRequest(request);
			if(StringUtils.hasText(token) && provider.validateJwtToken(token)) {
				String userName = provider.getUserNameFromToken(token);
				UserDetails userDetail = customUserDetailService.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken auth
					= new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("authenticated user " + userName + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}catch (Exception e) {
		}
		filterChain.doFilter(request, response);
	}

}
