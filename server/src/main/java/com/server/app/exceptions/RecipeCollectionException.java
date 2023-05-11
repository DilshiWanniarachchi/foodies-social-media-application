package com.server.app.exceptions;

public class RecipeCollectionException extends Exception {
    public RecipeCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Recipe with id " + id + " not found!";
    }

}
