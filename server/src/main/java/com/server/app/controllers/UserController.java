package com.server.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.app.models.User;
import com.server.app.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private User user;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/savePost")
    public ResponseEntity<?> savePost(@PathVariable("id") String id, @RequestParam("postId") String PostId) {
        try {
            user = userService.savePost(id, PostId);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/savedItems")
    public ResponseEntity<?> getSavedPosts(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(userService.getSavedPosts(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getfollowers(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(userService.getfollowers(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<?> getfollowing(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(userService.getfollowing(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/follow")
    public ResponseEntity<?> followUnfollowUser(@PathVariable("id") String id, @RequestParam("followingId") String followingId) {
        try {
            user = userService.followUnfollowUser(id, followingId);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
