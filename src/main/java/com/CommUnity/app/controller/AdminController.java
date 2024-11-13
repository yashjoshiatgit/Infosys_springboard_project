package com.CommUnity.app.controller;
import com.CommUnity.app.model.Admin;
import com.CommUnity.app.security.JwtTokenUtil;
import com.CommUnity.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUpAdmin(@RequestBody Admin admin) {
        String message = adminService.saveAdmin(admin, passwordEncoder::encode);
        Map<String, String> response = new HashMap<>();
        if (message.contains("successful!")) {
            response.put("status", "success");
            response.put("message", message);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signInAdmin(@RequestParam String username, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            if (auth.isAuthenticated()) {
                String token = jwtTokenUtil.generateToken(username);
                response.put("status", "success");
                response.put("message", "Sign-in successful! Welcome back.");
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                throw new Exception("Invalid username or password.");
            }
        } catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("message", "Invalid username or password. Please try again.");
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }
}
