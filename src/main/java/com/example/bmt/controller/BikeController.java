package com.example.bmt.controller;

import org.springframework.ui.Model;
import com.example.bmt.model.Product;
import com.example.bmt.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bikes")
public class BikeController {

    private final ProductRepository productRepository;

    // Constructor injection
    public BikeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/mt15")
    public String mt15Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("MT-15 Accessories");
        model.addAttribute("accessories", accessories);
        return "mt15"; // resolves to mt15.html
    }

    @GetMapping("/rs200")
    public String rs200Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("RS200 Accessories");
        model.addAttribute("accessories", accessories);
        return "rs200"; // resolves to rs200.html
    }

    @GetMapping("/r15")
    public String r15Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("R15 Accessories");
        model.addAttribute("accessories", accessories);
        return "r15"; // resolves to r15.html
    }

    @GetMapping("/duke200")
    public String duke200Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("Duke200 Accessories");
        model.addAttribute("accessories", accessories);
        return "duke200"; // resolves to duke200.html
    }

    @GetMapping("/ns200")
    public String ns200Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("NS200 Accessories");
        model.addAttribute("accessories", accessories);
        return "ns200"; // resolves to ns200.html
    }

    @GetMapping("/rc200")
    public String rc200Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("RC200 Accessories");
        model.addAttribute("accessories", accessories);
        return "rc200"; // resolves to rc200.html
    }

    @GetMapping("/gt650")
    public String gt650Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("GT650 Accessories");
        model.addAttribute("accessories", accessories);
        return "gt650"; // resolves to gt650.html
    }

    @GetMapping("/sf250")
    public String sf250Page(Model model) {
        List<Product> accessories = productRepository.findByCategoryName("SF250 Accessories");
        model.addAttribute("accessories", accessories);
        return "sf250"; // resolves to sf250.html
    }

}
