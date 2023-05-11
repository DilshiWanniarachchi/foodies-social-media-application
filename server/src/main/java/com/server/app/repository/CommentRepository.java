package com.server.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.server.app.models.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query("{'comment': ?0}")
    Optional<Comment> findByComment(String comment);

}

