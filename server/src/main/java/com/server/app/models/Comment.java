package com.server.app.models;

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
@Document(collection = "comment")
public class Comment {

    @Id
    private String id;

    @NotNull(message = "user cannot be null")
    private String user;

    @NotNull(message = "post cannot be null")
    private String post;

    private String comments;
    private Date createdAt;
    private Date updatedAt;

    public String getComment() {
        return null;
    }

}

