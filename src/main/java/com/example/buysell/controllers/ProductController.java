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
    public String createProduct(@RequestParam (value = "file") MultipartFile[] file ,
                                @ModelAttribute @Valid Product product, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return "new-product";
        productService.saveProduct(product, file);
        return "redirect:/";
    }

    @PostMapping("/product/new")
    public String newProduct(@RequestParam (value = "file") MultipartFile[] file,
                             @ModelAttribute @Valid Product product, BindingResult result) throws IOException {
        if (result.hasErrors())
            return "new-product";
        productService.saveProduct(product, file);
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
    public String updateProduct(@RequestParam ("file") MultipartFile [] file,
                                @Valid Product product,
                                BindingResult result, @PathVariable Long id) throws IOException {
        if (result.hasErrors()) {
            return "redirect:/product/{id}/product-edit";
        }
       productService.updateProduct(id, product, file);
       return "redirect:/product/{id}";
    }

    @PostMapping ("/product/images/delete/{id}")
    public String deleteImagesProduct(@PathVariable Long id, @ModelAttribute Product product){
        productService.deleteImageProduct(id, product);
        return "redirect:/product/{id}/product-edit";
    }


}