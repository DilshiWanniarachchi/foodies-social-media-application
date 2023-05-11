package com.server.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.app.exceptions.CommentCollectionException;
import com.server.app.models.Comment;
import com.server.app.repository.CommentRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class CommentServiceHandler implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // CREATE
    @Override
    public void createComment(Comment comment) throws ConstraintViolationException, CommentCollectionException {
        comment.setCreatedAt(new Date(System.currentTimeMillis()));

        System.out.println("Before save: " + comment.getId());
        commentRepository.save(comment);
        System.out.println("After save: " + comment.getId());
    }

    // GET ALL
    @Override
    public List<Comment> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        if (comments.size() > 0) {
            return comments;
        } else {
            return new ArrayList<Comment>();
        }
    }

    // DELETE
    @Override
    public List<Comment> deleteCommentById(String id) throws CommentCollectionException {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        List<Comment> comments;

        if (!commentOptional.isPresent()) {
            throw new CommentCollectionException(CommentCollectionException.NotFoundException(id));
        } else {
            commentRepository.deleteById(id);
            comments = commentRepository.findAll();
            return comments;
        }
    }

    // UPDATE
    @Override
    public void updateComment(String id, String comment)
            throws ConstraintViolationException, CommentCollectionException {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()) {
            throw new CommentCollectionException(CommentCollectionException.NotFoundException(id));
        } else {
            Comment existingComment = commentOptional.get();
            existingComment.setComments(comment);
            commentRepository.save(existingComment);
        }
    }

    /*
     * @Override
     * public List<?> getCommentsById(String id) {
     * return null;
     * // Optional<Comment> comment = commentRepository.findById(id);
     * // Comment commentToGet = comment.get();
     * // List<?> comments = commentToGet.getComments();
     * // return comments;
     * }
     */

}
