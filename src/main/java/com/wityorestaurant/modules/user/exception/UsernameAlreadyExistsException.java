package com.wityorestaurant.modules.user.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }
}
