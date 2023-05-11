package com.server.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.app.exceptions.UserCollectionException;
import com.server.app.models.Post;
import com.server.app.models.User;
import com.server.app.repository.PostRepository;
import com.server.app.repository.UserRepository;

@Service
public class UserServiceHandler implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public User getUserById(String id) throws UserCollectionException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        } else {
            return user.get();
        }
    }

	@Override
	public User savePost(String userId, String postId) throws UserCollectionException {
		Optional<User> optionalUser = userRepository.findById(userId);
        User updatedUser;
        if (!optionalUser.isPresent()) {
            throw new UserCollectionException(UserCollectionException.NotFoundException(userId));
        } else {
            User user = optionalUser.get();
            List<String> savedItems = user.getSavedItems();
            if (savedItems == null) {
                savedItems = new ArrayList<>();
            } else {
                if (savedItems.contains(postId)) {
                    savedItems.remove(postId);
                } else {
                    savedItems.add(postId);
                }
            }
            user.setSavedItems(savedItems);
            updatedUser = userRepository.save(user);
        }
        return updatedUser;
	}

    public List<Post> getSavedPosts(String userId) throws UserCollectionException {
        User user = getUserById(userId);
        List<String> savedItems = user.getSavedItems();
    
        List<Post> savedPosts = new ArrayList<>();
        for (String postId : savedItems) {
            Optional<Post> post = postRepository.findById(postId);
            if (post.isPresent()) {
                savedPosts.add(post.get());
            }
        }
    
        return savedPosts;
    }

    public List<User> getfollowers(String userId) throws UserCollectionException {
        User user = getUserById(userId);
        List<String> followers = user.getFollowers();

        List<User> myFollowers = new ArrayList<>();
        for (String id : followers) {
            Optional<User> OptionalUser = userRepository.findById(id);
            if (OptionalUser.isPresent()) {
                myFollowers.add(OptionalUser.get());
            }   
        }
        return myFollowers;
    }

    public List<User> getfollowing(String userId) throws UserCollectionException {
        User user = getUserById(userId);
        List<String> followings = user.getFollowing();

        List<User> myFollowings = new ArrayList<>();
        for (String id : followings) {
            Optional<User> OptionalUser = userRepository.findById(id);
            if (OptionalUser.isPresent()) {
                myFollowings.add(OptionalUser.get());
            }
        }
        return myFollowings;
    }

    public User followUnfollowUser(String followerId, String followingId) throws UserCollectionException {
        Optional<User> optionalUser = userRepository.findById(followerId);
        Optional<User> followingUser = userRepository.findById(followingId);

        User updatedUser;

        if (!optionalUser.isPresent()) {
            throw new UserCollectionException(UserCollectionException.NotFoundException(followerId));
        } else if (!followingUser.isPresent()) {
            throw new UserCollectionException(UserCollectionException.NotFoundException(followingId));
        } else {

            User user = optionalUser.get();
            List<String> followings = user.getFollowing();

            User following_user = followingUser.get();
            List<String> followers = following_user.getFollowers();

            if (followings == null) {
                followings = new ArrayList<>();
            } else {
                if (followings.contains(followingId)) {
                    followings.remove(followingId);
                } else {
                    followings.add(followingId);
                }
            }
            user.setFollowing(followings);
            updatedUser = userRepository.save(user);

            if (followers == null) {
                followers = new ArrayList<>();
            } else {
                if (followers.contains(followerId)) {
                    followers.remove(followerId);
                } else {
                    followers.add(followerId);
                }
            }
            following_user.setFollowers(followers);
            userRepository.save(following_user);
        }
        return updatedUser;
    }
}
