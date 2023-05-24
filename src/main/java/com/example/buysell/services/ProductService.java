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


    public void saveProduct(Product product, MultipartFile file1, MultipartFile file2,
                            MultipartFile file3) throws IOException {
      Image image1;
      Image image2;
      Image image3;

      if (file1.getSize()!=0){
          image1 = toImageEntity(file1);
          image1.setPreviewImage(true);
          product.addImageToProduct(image1);
      }
      if (file2.getSize()!=0){
          image2 = toImageEntity(file2);
          product.addImageToProduct(image2);
      }
      if (file3.getSize()!=0) {
          image3 = toImageEntity(file3);
          product.addImageToProduct(image3);
      }

        log.info("Saving new Product. Title: {}; Author: {}",
                product.getTitle(), product.getAuthor());

      if (!product.getImages().isEmpty() && product.getImages().get(0)!=null) {
          Product productFromDb = productRepository.save(product);
          product.setPreviewImageId(productFromDb.getImages().get(0).getId());
      }
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

    public void updateProduct(Long id, Product productUpdate) {
       productUpdate.setId(id);
       productUpdate.setDateOfCreated(LocalDateTime.now());
        log.info("Saving new Product. Title: {}; Author: {}",
                productUpdate.getTitle(), productUpdate.getAuthor());
       productRepository.save(productUpdate);
    }
}
