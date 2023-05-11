package com.server.app.repository;

import com.server.app.models.Post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PostRepository extends MongoRepository<Post, String> {
    @Query("{'post': ?0}")
    Optional<Post> findByPost(String post);

    List<Post> findByUserOrderByUser(String user);

}
