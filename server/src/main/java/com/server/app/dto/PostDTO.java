package com.server.app.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private String user;
    private String description;
    private String location;
    private MultipartFile[] files;
}
