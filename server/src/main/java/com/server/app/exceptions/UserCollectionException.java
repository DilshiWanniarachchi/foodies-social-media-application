package com.server.app.exceptions;

public class UserCollectionException extends Exception {
    public UserCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "User with given id not found!";
    }
}