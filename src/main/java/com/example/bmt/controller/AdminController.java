package com.example.bmt.controller;

import com.example.bmt.model.Product;
import com.example.bmt.repository.CategoryRepository;
import com.example.bmt.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller @RequestMapping("/admin")
public class AdminController {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public AdminController(ProductRepository p, CategoryRepository c) {
        this.productRepo = p; this.categoryRepo = c;
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("newProduct", new Product());
        return "admin/admin-products";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepo.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/products/{id}/update-image")
    public String updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        var product = productRepo.findById(id).orElseThrow();
        String uploadDir = "src/main/resources/static/images/";
        Files.createDirectories(Paths.get(uploadDir));
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + filename);
        Files.write(path, file.getBytes(), StandardOpenOption.CREATE);

        product.setImageUrl("/images/" + filename);
        productRepo.save(product);
        return "redirect:/admin/products";
    }
}
