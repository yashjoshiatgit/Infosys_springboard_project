package com.CommUnity.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "admins")
public class Admin {
    @Id
    private String id;

    private String username;
    private String password;
    private String role = "ADMIN";
    //Additional Information
    private String name;
    private String phoneNumber;
    private String societyName;
    private String flatNumber;
    private String postal;
}

