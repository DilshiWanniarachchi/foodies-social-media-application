package com.server.app.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import com.server.app.dto.CommentDTO;
import com.server.app.dto.UpdateCommentDTO;
import com.server.app.exceptions.CommentCollectionException;
import com.server.app.models.Comment;
import com.server.app.services.CommentService;

import jakarta.servlet.ServletContext;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("api/comments")
public class CommentController implements ServletContextAware {
    @Autowired
    private CommentService commentService;

    @Autowired
    private Comment comment;

    private ServletContext servletContext;

    // CREATE
    @PostMapping(value = "createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {

        System.out.println(commentDTO.getUser());
        System.out.println(commentDTO.getPost());
        try {

            comment.setId(new ObjectId().toString());
            comment.setUser(commentDTO.getUser());
            comment.setPost(commentDTO.getPost());
            comment.setComments(commentDTO.getComment());

            commentService.createComment(comment);
            return new ResponseEntity<Comment>(comment, HttpStatus.OK);

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (CommentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // GET ALL
    @RequestMapping(value = "readComments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllComment() {
        List<Comment> comment = commentService.getAllComment();
        return new ResponseEntity<>(comment, comment.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // DELETE
    @RequestMapping(value = "deleteComment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            List<Comment> comments = commentService.deleteCommentById(id);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (CommentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /*
     * @GetMapping("/getComments/{id}")
     * public ResponseEntity<?> getComments(@PathVariable("id") String id) {
     * List<?> comments = commentService.getCommentsById(id);
     * return new ResponseEntity<>(comments, HttpStatus.OK);
     * 
     * }
     */

    // UPDATE
    @PutMapping(value = "updateComment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") String id,
            @RequestBody UpdateCommentDTO updateCommentDTO) {
        try {
            String comment = updateCommentDTO.getComment();
            System.out.println(id + comment);
            commentService.updateComment(id, comment);
            return new ResponseEntity<>("Successfully updated the comment with id " + id, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (CommentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
