package com.server.app.services;

import java.util.List;

import com.server.app.exceptions.UserCollectionException;
import com.server.app.models.Post;
import com.server.app.models.User;

public interface UserService {

    public User getUserById(String id) throws UserCollectionException;
    public User savePost(String userId, String postId) throws UserCollectionException;
    public List<Post> getSavedPosts(String userId) throws UserCollectionException;
    public List<User> getfollowers(String userId) throws UserCollectionException;
    public List<User> getfollowing(String userId) throws UserCollectionException;
    public User followUnfollowUser(String followerId, String followingId) throws UserCollectionException;
}
