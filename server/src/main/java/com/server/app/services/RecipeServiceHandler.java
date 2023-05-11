package com.server.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.app.exceptions.RecipeCollectionException;
import com.server.app.models.Recipe;
import com.server.app.repository.RecipeRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class RecipeServiceHandler implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    
    //CREATE
    @Override
    public void createRecipe(Recipe recipe) throws ConstraintViolationException, RecipeCollectionException {
        recipe.setCreatedAt(new Date(System.currentTimeMillis()));
        recipeRepository.save(recipe);
    }

    //GET ALL
    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        if (recipes.size() > 0) {
            return recipes;
        }else {
            return new ArrayList<Recipe>();
        }
    }

    //GET by recipe ID
    @Override
	public Recipe getRecipeById(String id) throws RecipeCollectionException {
		Optional<Recipe> recipe = recipeRepository.findById(id);
        if (!recipe.isPresent()) {
            throw new RecipeCollectionException(RecipeCollectionException.NotFoundException(id));
        } else {
            return recipe.get();
        }
	}

    //GET by user ID
    @Override
    public List<Recipe> getRecipesByUserId(String user) throws RecipeCollectionException {
        List<Recipe> recipe = recipeRepository.findByUser(user);
        return recipe;
    }

    //DELETE
    @Override
    public List<Recipe> deleteRecipeById(String id) throws RecipeCollectionException {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (!recipeOptional.isPresent()) {
            throw new RecipeCollectionException(RecipeCollectionException.NotFoundException(id));
        } else {
            recipeRepository.deleteById(id);
            List<Recipe> recipe = recipeRepository.findAll();
            return recipe;
        }
    }

    @Override
    public Recipe updateRecipe(
        String id, 
        String title, 
        String description,
        List<String> ingredients,
        String prepMethod, 
        String servings, 
        String prepTime
    ) throws RecipeCollectionException {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            Recipe recipeToUpdate = recipe.get();

            recipeToUpdate.setTitle(title);
            recipeToUpdate.setDescription(description);
            recipeToUpdate.setIngredients(ingredients);
            recipeToUpdate.setPrepMethod(prepMethod);
            recipeToUpdate.setServings(servings);
            recipeToUpdate.setPrepTime(prepTime);
            recipeToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            Recipe updatedRecipe = recipeRepository.save(recipeToUpdate);
            return updatedRecipe;
        } else {
            throw new RecipeCollectionException(RecipeCollectionException.NotFoundException(id));
        }
    }

    
    
}
