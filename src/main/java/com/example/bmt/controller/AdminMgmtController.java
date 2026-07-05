package com.example.bmt.controller;

import com.example.bmt.model.Admin;
import com.example.bmt.model.AdminRole;
import com.example.bmt.repository.AdminRepository;
import com.example.bmt.repository.UserRepository;
import com.example.bmt.repository.OrderRepository;
import com.example.bmt.repository.PaymentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/admin/mgmt")
public class AdminMgmtController {
  private final AdminRepository adminRepo;
  private final UserRepository userRepo;
  private final OrderRepository orderRepo;
  private final PaymentRepository payRepo;
  private final PasswordEncoder encoder;

  public AdminMgmtController(AdminRepository a, UserRepository u, OrderRepository o, PaymentRepository p, PasswordEncoder e) {
    this.adminRepo=a; this.userRepo=u; this.orderRepo=o; this.payRepo=p; this.encoder=e;
  }

  private boolean isSuper(Authentication auth) {
    return auth.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals("ROLE_SUPER_ADMIN"));
  }

  @GetMapping
  public String page(Authentication auth, Model model,
                     @RequestParam(required=false) String q,
                     @RequestParam(required=false) String sort) {
    model.addAttribute("adminName", auth.getName());
    model.addAttribute("admins", adminRepo.findAll());
    model.addAttribute("users", userRepo.findAll());
    model.addAttribute("orders", orderRepo.findAll());
    model.addAttribute("payments", payRepo.findAll());
    model.addAttribute("newAdmin", new Admin());
    model.addAttribute("isSuper", isSuper(auth));
    model.addAttribute("q", q); model.addAttribute("sort", sort);
    return "admin/bmt-admins";
  }

  @PostMapping("/add")
  public String add(Authentication auth, @ModelAttribute Admin admin) {
    if (!isSuper(auth)) return "redirect:/admin/mgmt?error=forbidden";
    admin.setPassword(encoder.encode(admin.getPassword()));
    if (admin.getRole() == null) admin.setRole(AdminRole.ADMIN);
    adminRepo.save(admin);
    return "redirect:/admin/mgmt";
  }

  @PostMapping("/update/{id}")
  public String update(Authentication auth, @PathVariable Long id, @ModelAttribute Admin input) {
    if (!isSuper(auth)) return "redirect:/admin/mgmt?error=forbidden";
    var a = adminRepo.findById(id).orElseThrow();
    a.setUsername(input.getUsername());
    a.setEmail(input.getEmail());
    a.setPhone(input.getPhone());
    if (input.getPassword() != null && !input.getPassword().isBlank()) {
      a.setPassword(encoder.encode(input.getPassword()));
    }
    a.setRole(input.getRole());
    adminRepo.save(a);
    return "redirect:/admin/mgmt";
  }

  @PostMapping("/delete/{id}")
  public String delete(Authentication auth, @PathVariable Long id) {
    if (!isSuper(auth)) return "redirect:/admin/mgmt?error=forbidden";
    adminRepo.deleteById(id);
    return "redirect:/admin/mgmt";
  }
}
