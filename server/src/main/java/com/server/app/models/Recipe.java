package com.server.app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Document(collection = "recipes")
public class Recipe {
    
    @Id
    private String id;

    @NotNull(message = "user cannot be null")
    private String user;

    private String title;
    private String imagePath;
    private String description;
    private List<String> ingredients = new ArrayList<>();
    private String prepMethod;
    private String servings;
    private String prepTime;
    private Date createdAt;
    private Date updatedAt;
}
