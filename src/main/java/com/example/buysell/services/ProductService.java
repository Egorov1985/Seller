package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.repositories.ImageRepository;
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
    private final ImageRepository imageRepository;

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

        for (MultipartFile f: file){
            if (f.getSize()!=0){
                product.addImageToProduct(toImageEntity(f));
            }
        }

        Product productFromDb = productRepository.save(product);
        if (!productFromDb.getImages().isEmpty()) {
            product.setPreviewImageId(productFromDb.getImages().get(0).getId());
        }

        log.info("Saving new Product. Title: {}; Author: {}",
                product.getTitle(), product.getAuthor());
        productRepository.save(product);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void updateProduct(Long id, Product productUpdate, MultipartFile[] file) throws IOException {

        System.out.println(productUpdate);

        for (MultipartFile f: file){
            if (f.getSize()!=0){
                productUpdate.addImageToProduct(toImageEntity(f));
            }
        }
        if (!getProductById(id).getImages().isEmpty()){
            productUpdate.setPreviewImageId(getProductById(id).getImages().get(0).getId());
        }

        productUpdate.setDateOfCreated(LocalDateTime.now());
        log.info("Update  Product. Title: {}; Author: {}",
                productUpdate.getTitle(), productUpdate.getAuthor());
       productRepository.save(productUpdate);
    }

    public void deleteImageProduct(Long id, Product productDeleteImage) {
      // List <Long> integerList = new ArrayList<>();
      //
      // getProductById(id).getImages().forEach(image -> integerList.add(image.getId()));
      // System.out.println(integerList);
      // for (Long idImage: integerList){
      //     System.out.println(idImage);
      //     imageRepository.deleteById(idImage);
      // }

        System.out.println(id);
        System.out.println(productDeleteImage);
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



}
