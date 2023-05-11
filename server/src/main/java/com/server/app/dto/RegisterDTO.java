package com.server.app.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private String fullname;
    private String picturePath;
    private MultipartFile file;

}
