package com.server.app.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDTO {
    private String user;
    private String title;
    private MultipartFile file;
    private String description;
    private List<String> ingredients;
    private String prepMethod;
    private String servings;
    private String prepTime;
}
