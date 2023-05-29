package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> listProducts(String title) {
        List<Product> productList = productRepository.findAll();
        if (title!=null){
            List<Product> listSearch = new ArrayList<>();
            for (Product product: productList){
                if (product.getTitle().startsWith(title))
                    listSearch.add(product);
            }
            return listSearch;
        }
       return productList;
    }


    public void saveProduct(Product product, MultipartFile[] file) throws IOException {
        if (file.length!=0){
            int count = 0;
            for (MultipartFile f: file){
                product.addImageToProduct(toImageEntity(f));
                if (count==0){
                    product.getImages().get(0).setPreviewImage(true);
                    product.setPreviewImageId(product.getImages().get(0).getId());
                }
                count++;
            }
        }

        if (!product.getImages().isEmpty()) {
            product.setPreviewImageId(product.getImages().get(0).getId());
        }

        log.info("Saving new Product. Title: {}; Author: {}",
                product.getTitle(), product.getAuthor());
        productRepository.save(product);
    }

    private Image toImageEntity (MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void updateProduct(Long id, Product productUpdate, MultipartFile[] file) throws IOException {

       productUpdate.setPreviewImageId(productUpdate.getPreviewImageId());
       productUpdate.setDateOfCreated(LocalDateTime.now());
        log.info("Update  Product. Title: {}; Author: {}",
                productUpdate.getTitle(), productUpdate.getAuthor());
       productRepository.save(productUpdate);
    }
}
