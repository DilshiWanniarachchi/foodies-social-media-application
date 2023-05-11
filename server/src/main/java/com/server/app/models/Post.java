package com.server.app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
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
@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    @NotNull(message = "user cannot be null")
    @Field("user")
    private String user;

    @NotNull(message = "description cannot be null")
    private String description;
    private String location;
    private String[] pictures;
    private List<String> likes = new ArrayList<>();
    private Date createdAt;
    private Date updatedAt;

}
