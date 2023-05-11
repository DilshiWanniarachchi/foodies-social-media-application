package com.server.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.server.app.exceptions.PostCollectionException;
import com.server.app.models.Post;
import com.server.app.repository.PostRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class PostServiceHandler implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void createPost(Post post) throws ConstraintViolationException, PostCollectionException {
        post.setCreatedAt(new Date(System.currentTimeMillis()));
        System.out.println("Before save: " + post.getId());
        postRepository.save(post);
        System.out.println("After save: " + post.getId());
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.size() > 0) {
            return posts;
        } else {
            return new ArrayList<Post>();
        }
    }

    @Override
    public List<Post> deletePostById(String id) throws PostCollectionException {
        Optional<Post> post = postRepository.findById(id);
        List<Post> posts;
        if (!post.isPresent()) {
            throw new PostCollectionException(PostCollectionException.NotFoundException(id));
        } else {
            postRepository.deleteById(id);
            posts = postRepository.findAll();
            return posts;
        }

    }

    @Override
    public Post likePost(String postId, String userId) throws PostCollectionException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post updatedPost;
        if (!optionalPost.isPresent()) {
            throw new PostCollectionException(PostCollectionException.NotFoundException(postId));
        } else {

            Post post = optionalPost.get();
            List<String> likes = post.getLikes();
            if (likes == null) {
                likes = new ArrayList<>();
            } else {
                if (likes.contains(userId)) {
                    likes.remove(userId);
                } else {
                    likes.add(userId);
                }
            }
            post.setLikes(likes);
            updatedPost = postRepository.save(post);
        }

        return updatedPost;
    }

    @Override
    public List<Post> getPostsByUserId(String user) throws PostCollectionException {

        List<Post> posts = postRepository.findByUserOrderByUser(user);
        return posts;

    }

    @Override
    public Post updatePost(String id, String description, String location) throws PostCollectionException {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {

            Post postToUpdate = post.get();

            postToUpdate.setDescription(description);
            postToUpdate.setLocation(location);
            postToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            Post updatedPost = postRepository.save(postToUpdate);
            return updatedPost;
        } else {
            throw new PostCollectionException(PostCollectionException.NotFoundException(id));
        }
    }

    @Override
    public List<?> getLikesById(String id) {
        Optional<Post> post = postRepository.findById(id);
        Post postToGet = post.get();
        List<?> likes = postToGet.getLikes();
        return likes;
    }

}
