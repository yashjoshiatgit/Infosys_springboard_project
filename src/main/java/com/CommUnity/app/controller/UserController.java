package com.CommUnity.app.controller;

import com.CommUnity.app.model.User;
import com.CommUnity.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {

        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user); // Return user with HTTP 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Return HTTP 404 if user not found
        }
    }
}
