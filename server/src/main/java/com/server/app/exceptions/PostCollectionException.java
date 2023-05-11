package com.server.app.exceptions;

public class PostCollectionException extends Exception {
    public PostCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Post with given id not found!";
    }

    public static String NotFoundException2(String id) {
        return "There are no post with given user";
    }
}