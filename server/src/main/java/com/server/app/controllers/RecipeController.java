package com.server.app.controllers;

import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import org.bson.types.ObjectId;

import com.server.app.dto.RecipeDTO;
import com.server.app.dto.RecipeUpdateDTO;
import com.server.app.exceptions.RecipeCollectionException;
import com.server.app.models.Recipe;
import com.server.app.services.RecipeService;

import jakarta.servlet.ServletContext;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("api/recipes")
public class RecipeController implements ServletContextAware {

    private ServletContext servletContext;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private Recipe recipe;

    //CREATE
    @RequestMapping(value = "createRecipe", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> createRecipe(@ModelAttribute RecipeDTO recipeDTO) {
        String response;
        try {
            response = save(recipeDTO.getFile());
            System.out.println(response);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        try {
            recipe.setId(new ObjectId().toString());
            recipe.setUser(recipeDTO.getUser());
            recipe.setTitle(recipeDTO.getTitle());
            recipe.setImagePath(response);
            recipe.setDescription(recipeDTO.getDescription());
            recipe.setIngredients(recipeDTO.getIngredients());
            recipe.setPrepMethod(recipeDTO.getPrepMethod());
            recipe.setServings(recipeDTO.getServings());
            recipe.setPrepTime(recipeDTO.getPrepTime());

            recipeService.createRecipe(recipe);
            return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (RecipeCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
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

    //GET ALL
    @GetMapping("/getRecipes")
    public ResponseEntity<?> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, recipes.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //GET By recipe ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //GET By user ID
    @GetMapping("/getRecipesByUser/{id}")
    public ResponseEntity<?> getRecipeByUser(@PathVariable("id") String user) {

        try {
            List<Recipe> recipes = recipeService.getRecipesByUserId(user);
            return new ResponseEntity<>(recipes, recipes.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (RecipeCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    //DELETE
    @DeleteMapping("deleteRecipe/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            List<Recipe> recipes = recipeService.deleteRecipeById(id);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (RecipeCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PutMapping("/updateRecipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") String id, @RequestBody RecipeUpdateDTO recipeUpdateDTO) {
        
        try {
            String title = recipeUpdateDTO.getTitle();
            String description = recipeUpdateDTO.getDescription();
            List<String> ingredients = recipeUpdateDTO.getIngredients();
            String prepMethod = recipeUpdateDTO.getPrepMethod();
            String servings = recipeUpdateDTO.getServings();
            String prepTime = recipeUpdateDTO.getPrepTime();
            recipe = recipeService.updateRecipe(id, title, description, ingredients, prepMethod, servings, prepTime);
            return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (RecipeCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}
