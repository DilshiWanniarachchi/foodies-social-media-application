package com.server.app.services;

import java.util.List;

import com.server.app.exceptions.CommentCollectionException;
import com.server.app.models.Comment;

import jakarta.validation.ConstraintViolationException;

public interface CommentService {
    public void createComment(Comment comment) throws ConstraintViolationException, CommentCollectionException;

    public List<Comment> getAllComment();

    public List<Comment> deleteCommentById(String id) throws CommentCollectionException;

    public void updateComment(String id, String comment)
            throws ConstraintViolationException, CommentCollectionException;
    // public List<?> getCommentsById(String id);

}
