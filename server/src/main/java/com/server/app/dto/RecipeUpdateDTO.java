package com.server.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeUpdateDTO {
    private String title;
    private String description;
    private List<String> ingredients;
    private String prepMethod;
    private String servings;
    private String prepTime;
}
