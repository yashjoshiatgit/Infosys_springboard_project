package com.CommUnity.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;
    private String password;
    private String role = "RESIDENT";
    private String name;
    private String phoneNumber;
    private String societyName;
    private String city;
    private String district;
    private String postal;

    public String getRoles() {
        return role;
    }
}

