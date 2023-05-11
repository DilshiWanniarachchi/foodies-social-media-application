package com.server.app.services;

import java.util.List;

import com.server.app.exceptions.PostCollectionException;
import com.server.app.models.Post;

import jakarta.validation.ConstraintViolationException;

public interface PostService {

    public void createPost(Post post) throws ConstraintViolationException, PostCollectionException;

    public List<Post> getAllPosts();

    public List<Post> deletePostById(String id) throws PostCollectionException;

    public Post likePost(String postId, String userId) throws PostCollectionException;

    public List<Post> getPostsByUserId(String user) throws PostCollectionException;

    public Post updatePost(String id, String description, String location) throws PostCollectionException;

    public List<?> getLikesById(String id);

}
