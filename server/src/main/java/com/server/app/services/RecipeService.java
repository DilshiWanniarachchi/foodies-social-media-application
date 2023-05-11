package com.server.app.services;

import java.util.List;

import com.server.app.exceptions.RecipeCollectionException;
import com.server.app.models.Recipe;

import jakarta.validation.ConstraintViolationException;

public interface RecipeService {
    public void createRecipe(Recipe recipe) throws ConstraintViolationException, RecipeCollectionException;
    public List<Recipe> getAllRecipes();
    public List<Recipe> getRecipesByUserId(String user) throws RecipeCollectionException;
    public Recipe getRecipeById(String id) throws RecipeCollectionException;
    public List<Recipe> deleteRecipeById(String id) throws RecipeCollectionException;
    public Recipe updateRecipe(
        String id, 
        String title, 
        String description,
        List<String> ingredients,
        String prepMethod, 
        String servings, 
        String prepTime
    ) throws RecipeCollectionException;
}
