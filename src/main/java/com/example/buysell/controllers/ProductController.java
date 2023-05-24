package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Model model) {
        model.addAttribute("products", productService.listProducts(title));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam ("file1") MultipartFile file1,
                                @RequestParam ("file2") MultipartFile file2,
                                @RequestParam ("file3") MultipartFile file3,
                                @Valid Product product, BindingResult result) throws IOException {
        if (result.hasErrors())
            return "new-product";
        productService.saveProduct(product, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/product/new")
    public String newProduct(@RequestParam (value = "file1") MultipartFile file1,
                             @RequestParam (value = "file2") MultipartFile file2,
                             @RequestParam (value = "file3") MultipartFile file3,
                             @Valid Product product, BindingResult result) throws IOException {
        if (result.hasErrors())
            return "new-product";
        productService.saveProduct(product, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/product/{id}/product-edit")
    public String editProduct(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-edit";
    }


    @PostMapping("/product/{id}/update")
    public String updateProduct(@RequestParam ("file1") MultipartFile file1,
                                @RequestParam ("file1") MultipartFile file2,
                                @RequestParam ("file1") MultipartFile file3,
                                @ModelAttribute("product") @Valid Product product,
                                BindingResult result, @PathVariable Long id) throws IOException {
        if (result.hasErrors()) {
            System.out.println(result.toString());
            return "redirect:/product/{id}/product-edit";
        }
       productService.updateProduct(id, product, file1, file2, file3);
       return "redirect:/product/{id}";
    }





}