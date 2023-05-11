package com.server.app.repository;

import com.server.app.models.Recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    @Query("{'recipe': ?0}")
    Optional<Recipe> findByRecipe(String recipe);
    List<Recipe> findByUser(String user);
}
