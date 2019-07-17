package com.wityorestaurant.common.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class WityoExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<?> handleAllControllerExceptions(Exception ex){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("errorMessage", ex.getMessage());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

}
