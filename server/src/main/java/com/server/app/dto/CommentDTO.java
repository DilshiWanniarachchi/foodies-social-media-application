package com.server.app.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {
    private String id;
    private String user;
    private String post;
    private String comment;
    
}
