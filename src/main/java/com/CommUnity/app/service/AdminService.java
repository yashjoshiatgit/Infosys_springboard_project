package com.CommUnity.app.service;

import com.CommUnity.app.model.Admin;
import com.CommUnity.app.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public String saveAdmin(Admin admin, Function<String, String> passwordEncoder) {
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()) {
            return "Username already exists. Please choose a different username.";
        }

        if (!isValidPassword(admin.getPassword())) {
            return "Password must be at least 8 characters long.";
        }

        if (admin.getPhoneNumber() == null || admin.getPostal() == null) {
            return "Phone number and postal code are mandatory.";
        }

        admin.setPassword(passwordEncoder.apply(admin.getPassword()));
        adminRepository.save(admin);
        return "Sign-up successful! Please sign in.";
    }


    private boolean isValidPassword(String password) {
        // Check password length and complexity
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    public Optional<Admin> authenticate(String username, String password) {
        return adminRepository.findByUsername(username)
                .filter(admin -> admin.getPassword().equals(password));
    }
}


