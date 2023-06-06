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
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false
            ) String title, Model model, Principal principal) {
        List<Product> productList = productService.listProducts(title);
        model.addAttribute("products", productList);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("productUserId", product.getUser().getId());
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "product-info";
    }

    @PostMapping ("/product/create")
    public String createProduct(@RequestParam (value = "file") MultipartFile[] file ,
                                @ModelAttribute @Valid Product product,
                                BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors())
            return "new-product";
        productService.saveProduct(principal, product, file);
        return "redirect:/";
    }

    @GetMapping("/product/new")
    public String newProduct() throws IOException {
        return "new-product";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/product/{id}/product-edit")
    public String editProduct(@PathVariable Long id, Model model, Principal principal){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "product-edit";
    }


    @PostMapping("/product/{id}/update")
    public String updateProduct(@RequestParam ("file") MultipartFile [] file,
                                @PathVariable Long id) throws IOException {
       productService.updateProduct(id, file);
       return "redirect:/product/{id}";
    }


    @GetMapping ("/product/images/delete/{id}")
    public String deleteImagesProduct(@PathVariable Long id){
        productService.deleteImagesOfProduct(id);
        return "redirect:/product/{id}";
    }

}