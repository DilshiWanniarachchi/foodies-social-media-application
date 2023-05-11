package com.server.app.exceptions;

public class CommentCollectionException extends Exception {
    public CommentCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Comment with id"+ id + "not found!";
    }    
}
