package com.example.bmt.controller;

import com.example.bmt.model.User;
import com.example.bmt.repository.UserRepository;
import com.example.bmt.util.EncryptionUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/auth")
public class AuthController {
  private final UserRepository userRepo;
  private final PasswordEncoder encoder;
  public AuthController(UserRepository u, PasswordEncoder e){ this.userRepo=u; this.encoder=e; }

  @GetMapping("/login")
  public String login(@RequestParam(value="error", required=false) String error, Model model){
    if (error != null) model.addAttribute("loginError", "Username or password is wrong.");
    return "login";
  }

  @GetMapping("/register")
  public String register(Model model){ model.addAttribute("user", new User()); return "register"; }

  @PostMapping("/register")
  public String doRegister(@Valid @ModelAttribute User user, BindingResult br, Model model){
    if (br.hasErrors()) return "register";
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRole("ROLE_CUSTOMER");
    // store encrypted fields for demo (email/phone) alongside plain stored fields
    user.setEmail(EncryptionUtil.encrypt(user.getEmail()));
    user.setPhone(EncryptionUtil.encrypt(user.getPhone()));
    userRepo.save(user);
    return "redirect:/auth/login";
  }

  @GetMapping("/forgot")
  public String forgot(Model model){ return "forgot-password"; }

  @PostMapping("/forgot")
  public String verify(@RequestParam String username, @RequestParam String phone, Model model){
    var userOpt = userRepo.findByUsername(username);
    if (userOpt.isEmpty()) { model.addAttribute("error","Username not found"); return "forgot-password"; }
    var user = userOpt.get();
    String decPhone = com.example.bmt.util.EncryptionUtil.decrypt(user.getPhone());
    if (!decPhone.equals(phone)) { model.addAttribute("error","Phone does not match"); return "forgot-password"; }
    model.addAttribute("username", username);
    return "reset-password";
  }

  @PostMapping("/reset")
  public String reset(@RequestParam String username, @RequestParam String password, Model model){
    var userOpt = userRepo.findByUsername(username);
    if (userOpt.isEmpty()) { model.addAttribute("error","User not found"); return "reset-password"; }
    var user = userOpt.get();
    user.setPassword(encoder.encode(password));
    userRepo.save(user);
    return "redirect:/auth/login?reset";
  }
}
