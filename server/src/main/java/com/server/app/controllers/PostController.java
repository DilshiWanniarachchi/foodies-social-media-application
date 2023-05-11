package com.server.app.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.server.app.dto.PostDTO;
import com.server.app.dto.PostUpdateDTO;
import com.server.app.exceptions.PostCollectionException;
import com.server.app.models.Post;
import com.server.app.services.PostService;

import jakarta.servlet.ServletContext;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("api/posts")
public class PostController implements ServletContextAware {

    private ServletContext servletContext;

    @Autowired
    private PostService postService;

    @Autowired
    private Post post;

    @RequestMapping(value = "createPost", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> createPost(@ModelAttribute PostDTO postDTO) {
        String response;
        String[] picturePaths = new String[4];
        int i = 0;

        try {
            for (MultipartFile file : postDTO.getFiles()) {
                response = save(file);
                System.out.println(response);
                picturePaths[i] = response;
                System.out.println(picturePaths);
                i++;
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }

        try {

            post.setId(new ObjectId().toString());
            post.setUser(postDTO.getUser());
            post.setDescription(postDTO.getDescription());
            post.setLocation(postDTO.getLocation());
            post.setPictures(picturePaths);

            System.out.println(post.toString());
            postService.createPost(post);
            return new ResponseEntity<Post>(post, HttpStatus.OK);

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (PostCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getPosts")
    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, posts.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") String id) {
        try {
            List<Post> posts = postService.deletePostById(id);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (PostCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getLikes/{id}")
    public ResponseEntity<?> getLikes(@PathVariable("id") String id) {
        List<?> likes = postService.getLikesById(id);
        return new ResponseEntity<>(likes, HttpStatus.OK);

    }

    @PutMapping("/{id}/likePost")
    public ResponseEntity<?> likePost(@PathVariable("id") String id, @RequestParam("userId") String userId) {
        try {
            post = postService.likePost(id, userId);
            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPostsByUser/{id}")
    public ResponseEntity<?> getPostByUser(@PathVariable("id") String user) {

        try {
            List<Post> posts = postService.getPostsByUserId(user);
            return new ResponseEntity<>(posts, posts.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);

        } catch (PostCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/updatePost/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") String id, @RequestBody PostUpdateDTO postUpdateDTO) {
        try {
            String description = postUpdateDTO.getDescription();
            String location = postUpdateDTO.getLocation();
            post = postService.updatePost(id, description, location);
            return new ResponseEntity<Post>(post, HttpStatus.OK);

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (PostCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public String save(MultipartFile file) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
            String newFileName = simpleDateFormat.format(new Date()) + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(this.servletContext.getRealPath("/uploads/images/" + newFileName));
            Files.write(path, bytes);
            return newFileName;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
