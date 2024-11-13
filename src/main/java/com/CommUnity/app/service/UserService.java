package com.CommUnity.app.service;

import com.CommUnity.app.model.User;
import com.CommUnity.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveUser(User user, Function<String, String> passwordEncoder) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists. Please choose a different username.";
        }

        if (!isValidPassword(user.getPassword())) {
            return "Password must be at least 8 characters long and include a mix of letters and numbers.";
        }

        if (user.getPhoneNumber() == null || user.getPostal() == null) {
            return "Phone number and postal code are mandatory.";
        }

        user.setPassword(passwordEncoder.apply(user.getPassword()));
        userRepository.save(user);
        return "Sign-up successful! Please sign in.";
    }


    private boolean isValidPassword(String password) {
        // Check password length and complexity (at least 8 characters, including one digit)
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }
}
