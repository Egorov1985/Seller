package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String products(Model model, Principal principal) {
        List<Product> productList = productService.allProduct();
        model.addAttribute("products", productList);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }
}
