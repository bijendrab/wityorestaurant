package com.wityorestaurant.security.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.wityorestaurant.common.Constant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import static com.wityorestaurant.common.Constant.AUTHORITIES_KEY;

@Component
public class JwtTokenProvider {
	
	Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	/*---------------------------GENERATING THE TOKEN--------------------------------*/
	
	public String generateJwtToken(Authentication auth) {

		//estaurantUser user = (RestaurantUser) auth.getPrincipal();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		final String authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(now.getTime() + Constant.EXPIRY_TIME);
		//String userId = Long.toString(((RestaurantUser) auth.getPrincipal()).getUserId());
		String userName = auth.getName();
		//Map<String, Object> claims = new HashMap<String, Object>();
		//Claims claims = Jwts.claims().setSubject(customerName);
		//claims.put("id", userId);
		//claims.put("fullName", customerName);
		//claims.put("Role",authorities);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constant.API_SECRET);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return Jwts.builder()
				   .setSubject(userName)
				   .claim("userName",userName)
				   .claim("scopes",authorities)
				   .setIssuedAt(now)
				   .setExpiration(expiryDate)
				   .signWith(signatureAlgorithm, signingKey)
				   .compact();
	}
	
	/*---------------------------GENERATING THE TOKEN--------------------------------*/
	
	

	/*---------------------------VALIDATING THE TOKEN--------------------------------*/
	
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(Constant.API_SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
		} catch (MalformedJwtException e) {			
		} catch (ExpiredJwtException e) {
		} catch (UnsupportedJwtException e) {
		} catch (IllegalArgumentException e) {}
		
		return false;
	}
	
	/*---------------------------VALIDATING THE TOKEN--------------------------------*/
	
	
	
	/*---------------------------GETTING USERID FROM THE TOKEN-------------------------*/
	
	public Long getUserIdFromToken(String token) {
		Claims claim = Jwts.parser()
						   .setSigningKey(Constant.API_SECRET)
						   .parseClaimsJws(token)
						   .getBody();
		String userId = (String)claim.get("id");
		return Long.parseLong(userId);
	}
	
	/*---------------------------GETTING USERNAME FROM THE TOKEN-------------------------*/
	public String getUserNameFromToken(String token) {
		Claims claim = Jwts.parser()
				.setSigningKey(Constant.API_SECRET)
				.parseClaimsJws(token)
				.getBody();
		String userName = (String)claim.get("userName");
		return userName ;
	}
}
