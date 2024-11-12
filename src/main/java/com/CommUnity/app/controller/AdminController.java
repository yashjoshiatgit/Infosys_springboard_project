package com.CommUnity.app.controller;

import com.CommUnity.app.model.Admin;
import com.CommUnity.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Optional<Admin>> getAdmin(@PathVariable String username) {

        Optional<Admin> foundAdmin = adminService.findByUsername(username);

        if (foundAdmin.isPresent()) {
            return ResponseEntity.ok(foundAdmin); // HTTP 200 OK if found
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if not found
        }
    }
}
