package com.wityorestaurant.security.dto;

public class JwtSuccessDto {
	
	private boolean success;
	private String token;
	
	public JwtSuccessDto(boolean success, String token) {
		super();
		this.success = success;
		this.token = token;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "JwtSuccessDto [success=" + success + ", token=" + token + "]";
	}
	
	

}
