package com.example.bmt.controller;
//create user in DB.
import com.example.bmt.model.User;
import com.example.bmt.repository.UserRepository;
import com.example.bmt.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    // Create new user with AES + BCrypt
    @PostMapping("/create")
    public User createUser(@RequestBody UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(EncryptionUtil.encrypt(request.getEmail()));
        user.setPhone(EncryptionUtil.encrypt(request.getPhone()));
        user.setPassword(encoder.encode(request.getPassword())); // BCrypt
        user.setRole(request.getRole());
        user.setImg(request.getImg());
        return userRepo.save(user);
    }

    // DTO for request body
    public static class UserRequest {
        private String username;
        private String email;
        private String phone;
        private String password;
        private String role;
        private String img;

        // getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public void setImg(String img) { this.img = img; }
        public String getImg() { return img; }
    }
}
